# 🏨 Hotel Management System (Java Console Application)

## 📌 Overview

This is a console-based **Hotel Management System** developed in Java using Object-Oriented Programming principles. The system allows hotel staff to manage rooms, customers, food orders, billing, and data persistence through file serialization.

The application stores hotel data locally and restores it when restarted.

---

## 🛠 Technologies Used

* Java
* Object-Oriented Programming (OOP)
* Inheritance & Polymorphism
* Collections Framework (ArrayList)
* Exception Handling
* File Handling
* Serialization
* Multithreading

---

## 🧱 Project Structure

### Main Classes

| Class Name   | Description                                                |
| ------------ | ---------------------------------------------------------- |
| `Food`       | Represents food items ordered by customers                 |
| `Singleroom` | Stores details of single room occupants                    |
| `Doubleroom` | Extends `Singleroom` to store two occupants                |
| `holder`     | Contains arrays of all room types                          |
| `Hotel`      | Handles booking, billing, ordering, and availability logic |
| `write`      | Saves hotel data using serialization in a separate thread  |
| `Main`       | Entry point of the application                             |

---

## 🏠 Room Types & Pricing

| Room Type          | Price Per Day |
| ------------------ | ------------- |
| Luxury Double Room | ₹4000         |
| Deluxe Double Room | ₹3000         |
| Luxury Single Room | ₹2200         |
| Deluxe Single Room | ₹1200         |

---

## 🍽 Food Menu

| Item     | Price |
| -------- | ----- |
| Sandwich | ₹50   |
| Pasta    | ₹60   |
| Noodles  | ₹70   |
| Coke     | ₹30   |

Food charges are added to the final bill during checkout.

---

## ⚙️ Features

* Display room details
* Check room availability
* Book rooms
* Order food for booked rooms
* Generate final bill
* Checkout functionality
* Automatic data backup on exit
* Data restoration on restart

---

## 💾 Data Persistence

The system stores hotel data in a file named:

```
backup
```

### How It Works

* On startup: If the backup file exists, previous data is loaded.
* On exit: Current hotel state is saved using `ObjectOutputStream`.

---

## 🚀 How to Run

### Step 1: Compile

```
javac Main.java
```

### Step 2: Run

```
java Main
```

---

## 📋 Application Menu

When executed, the program provides the following options:

1. Display room details
2. Display room availability
3. Book room
4. Order food
5. Checkout
6. Exit

---

## 🧠 Concepts Demonstrated

* Inheritance (`Doubleroom` extends `Singleroom`)
* Polymorphism
* Custom Exception Handling (`NotAvailable`)
* Java Serialization
* Multithreading
* Collection Framework usage
* Switch-case control logic

---

## 📈 Possible Enhancements

* Improve input validation
* Replace arrays with dynamic collections
* Introduce Enums for room types
* Implement database integration (MySQL)
* Apply MVC architecture
* Build GUI (JavaFX/Swing)
* Convert to web-based application (Spring Boot)

---

## 👨‍💻 Author

**Aditya Bhardwaj**
B.Tech – CSE

---

## 🎯 Learning Outcomes

This project demonstrates practical implementation of Java fundamentals and system design concepts through a real-world inspired hotel management workflow.
