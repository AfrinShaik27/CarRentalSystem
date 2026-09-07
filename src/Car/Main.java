package Car; 
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

//Car class
class Car
{
	private String carId;
	private String brand;
	private String model;
	private double basePricePerDay;
	private boolean isAvailable;
	
	public Car(String carId,String brand,String model,double basePricePerDay)
	{
		this.carId=carId;
		this.brand=brand;
		this.model=model;
		this.basePricePerDay=basePricePerDay;
		this.isAvailable=true;
	}
	public String getCarId()
	{
		return carId;
	}
	public String getBrand()
	{
		return brand;
	}
	public String getModel()
	{
		return model;
	}
	public double calculatePrice(int rentalDays)
	{
		return basePricePerDay*rentalDays;
	}
	public boolean isAvailable()
	{
		return isAvailable;
	}
	public void rent()
	{
		isAvailable =false;
	}
	public void returnCar()
	{
		isAvailable=true;
	}
}

//class Customer

	class Customer
	{
		private String customerId;
		private String name;
		
		public Customer(String customerId,String name)
		{
			this.customerId=customerId;
			this.name=name;
					
		}
		
		public String getCustomerId() {
			 return customerId;
		}
		
		public String getName() {
			return name;
		}
	}
	
	//class Rental
	
	class Rental
	{
		private Car car;
		private Customer customer;
		private int days;
		
		public Rental(Car car,Customer customer,int days)
		{
			this.car=car;
			this.customer=customer;
			this.days=days;
		}
		public Car getCar()
		{
			return car;
		}
		public Customer getcustomer()
		{
			return customer;
		}
		
		public int getDays()
		{
			return days;
		}
	}
	
class CarRentalSystem
	{
		private List<Car> cars;
		private List<Customer> customers;
		private List<Rental>rentals;
		
		
		public CarRentalSystem()
		{
			cars=new ArrayList<>();
			customers=new ArrayList<>();
			rentals=new ArrayList<>();
		}
		
		public void addCars(Car car)
		{
			cars.add(car);
		}
		public void addCustomer(Customer customer)
		{
			customers.add(customer);
		}
		
		public void rentCar(Car car,Customer customer,int days)
		{
			if(car.isAvailable())
			{
				car.rent();
				rentals.add(new Rental(car,customer,days));
			}
			else
			{
				System.out.println("Car is not Available for Rent");
			}
		}
		public void returnCar(Car car)
		{
			car.returnCar();
		    Rental rentalToRemove=null;
			for(Rental rental:rentals)
			{
				if(rental.getCar()==car)
				{
					rentalToRemove=rental;
					break;
				}
			}
			if(rentalToRemove!=null)
			{
				rentals.remove(rentalToRemove);
				System.out.println("Car Returned Successfully");
			}
			else
			{
				System.out.println("Car was not Returned");
			}
		}
		
		public void menu()
		{
			Scanner sc=new Scanner(System.in);
			
			while(true)
			{
				System.out.println("==== Car Rental System ====");
				System.out.println("1.Rent a Car");
				System.out.println("2.Return a Car");
				System.out.println("3.Exit");
				System.out.print("Enter Your Choice:");
				
				int choice=sc.nextInt();
				sc.nextLine();
				
				if(choice==1)
				{
					System.out.println("\n === Rent a Car ===\n");
					System.out.print("Enter your name: ");
					String customerName=sc.nextLine();
					
					System.out.println("\nAvailable Cars:");
					for(Car car:cars)
					{
						if(car.isAvailable())
						{
							System.out.println(car.getCarId()+ " - "+car.getBrand()+" - "+car.getModel());
						}
					}
					System.out.print("\nEnter the car ID you want to rent:");
					String carId=sc.nextLine();
					
					System.out.print("Enter the number of days for rental:");
					int rentalDays=sc.nextInt();
					
					sc.nextLine();
					 
					Customer newcustomer=new Customer("CUS"+(customers.size()+1),customerName);
					addCustomer(newcustomer);
					
					
					
					Car selectedCar=null;
					for(Car car:cars)
					{
						if((car.getCarId().equals(carId))&& (car.isAvailable()));
						{
							selectedCar=car;
							break;
						}
					}
					
					if(selectedCar!=null)
					{
						double totalPrice=selectedCar.calculatePrice(rentalDays);
						System.out.println("\n Rental Information \n");
						System.out.println("Customer ID: "+newcustomer.getCustomerId());
						System.out.println("Customer Name: "+newcustomer.getName());
						System.out.println("Car: "+selectedCar.getBrand()+" "+selectedCar.getModel());
						System.out.println("Rental Days: " +rentalDays);
						System.out.printf("Total Price:$%.2f%n",totalPrice);
						
						System.out.print("\nConfirm Rental Y/N:\n");
						String confirm=sc.nextLine();
						
						if(confirm.equalsIgnoreCase("Y"))
						{
							rentCar(selectedCar,newcustomer,rentalDays);
							System.out.println("\nCar Rented Succssfully.");
						}
						else
						{
							System.out.println("\nRental Canceled.");
						}
					}
					else
					{
						System.out.println("Invalid Car Selection or Car is not available for rent");
					}
					
					
					
				}
				else if(choice==2)
				{
					System.out.println("\n===Return a Car===\n");
					System.out.println("Enter a car ID you want to return: ");
					String carId=sc.nextLine();
					
					Car carToReturn=null;
					for(Car car:cars)
					{
						if(car.getCarId().equals(carId) && !car.isAvailable())
						{
							carToReturn=car;
							break;
						}
					}
					
					if(carToReturn!=null)
					{
						Customer customer=null;
						for(Rental rental:rentals)
						{
							if(rental.getCar()==carToReturn)
							{
								customer=rental.getcustomer();
								break;
							}
						}
						
						if(customer!=null)
						{
							returnCar(carToReturn);
							System.out.println("Car returned Successfully by " +customer.getName());
							
						}else
						{
							System.out.println("Car was not returned or Rental Details is missing");
						}
					}
					else
					{
						System.out.println("Invalid car ID or car is not returned");
					}
					
					
				}
				else if(choice==3)
				{
					break;
				}
				else 
					
				{
					System.out.println("Invalid choice, Please Enter a valid option");
				}
				
			}
			System.out.println("\n Thank you for using Car Rental System");
			
		}
		
	}
public class Main
{
	public static void main(String args[])
	{
		CarRentalSystem rentalSystem=new CarRentalSystem();
		Car car1=new Car("C001","Toyato","Camry",60.0);
		Car car2=new Car("C002","Honda","Accord",70.0);
		Car car3=new Car("C003","Mahindra","Thar",150.0);
		
		rentalSystem.addCars(car1);
		rentalSystem.addCars(car2);
		rentalSystem.addCars(car3);
		
		rentalSystem.menu();
				
	}
}