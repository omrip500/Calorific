# Calorific

## Description
Calorific is designed to help individuals track their daily calorie intake and expenditure through a simple and user-friendly interface suitable for everyone.

## Features
- **User Registration and Login:** Register and log in using email, phone number, or Google account.
- **User Profile Management:** Fill in and update user details easily at any time.
- **Add Meal by Search:** Add meals from a database by searching.
- **Add Prepared Meal:** Add a prepared meal by entering its details (calories, grams of carbohydrates, protein, and fat).
- **Edit Prepared Meal:** Edit existing prepared meals.
- **Delete Prepared Meal:** Remove meals that are no longer needed.
- **Daily Summary:** View a summary of the day so far, including meals consumed, exercises performed, and their details.
- **Delete Meals and Exercises:** Remove meals or exercises if desired by the user.
- **Add Exercise from Database:** Add an exercise from a database that calculates calorie consumption based on the user's weight.
- **Daily Reset:** Reset all data at the end of the day to start fresh.

## Development
- **Firebase Authentication:** Used for user authentication during registration and login.
- **Firestore Database:** Manages storage of user data, including personal details, meals, and exercises.
- **Nutrition Information API:** Integrated a third-party API to retrieve detailed nutritional information about food items, enhancing the accuracy and ease of adding meals.

- **Activities:** Implemented to represent different screens of the application, managing user interactions and data display.
- **Data Models:** Created to encapsulate core entities such as users, meals, and exercises, ensuring organized data management.
  
- **Adapters:**
  - **ReadyMealAdapter:** Manages the list of user-prepared meals, allowing for easy editing or deletion.
  - **FoodApiAdapter:** Handles the integration and display of nutritional information retrieved from the external API.


## Screenshots

### 1. Registration and Login
<img src="app/screenshots/authentication.jpg" alt="Authentication" width="400"/>
<img src="app/screenshots/auth_code.jpg" alt="Authentication Code" width="400"/>

### 2. Home Screen
<img src="app/screenshots/home_screen.jpg" alt="Home Screen" width="400"/>

### 3. Menu
<img src="app/screenshots/menu.jpg" alt="Menu" width="400"/>

### 4. Adding Meals
<img src="app/screenshots/add_food.jpg" alt="Add Food" width="400"/>

### 5. Adding Exercices
<img src="app/screenshots/add_exercise.jpg" alt="Add Prepared Meal" width="400"/>

### 6. Daily Summary
<img src="app/screenshots/day_summary_1.jpg" alt="Day Summary - Part 1" width="400"/>
<img src="app/screenshots/day_summary_2.jpg" alt="Day Summary - Part 2" width="400"/>



