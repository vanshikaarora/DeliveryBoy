# DeliveryBoy
An android application that fetches all order details from client app to display these details to a delivery boy who can then manage the order and communicate with customer. All the backend work in this app has been done using firebase

# Backend
Using Firebase

# Preview
This is a simple deliveryBoy app wherein we fetch data of order placed by FoodHaunt App. Then display all such orders in a recycler view along with the order details in the app. The format for each order is described as below.

<img src="https://github.com/vanshikaarora/DeliveryBoy/blob/master/app/src/main/res/drawable-v21/readme_a.png">

By tapping on the **"Order confirmed by Food Haunt"** we update our data on firebase for FoodHaunt confirmation successful thus update user's timelineView. Successfully by tapping the button **"Order confirmed by FoodHaunt"** the value is changed from **"Order confirmed By Food Haunt"** -> **"Order confirmed by restaurant"** -> **"Picked up"** -> **"Delivered"**. simultaneously updating corresponding parameters on Firebase and thus updating FoodHaunt users TimeLineView.

# Specific details

By tapping individually on the order, we can specifically locate to details of the customer along with more functionalities to update customer about the order.

<img src="https://github.com/vanshikaarora/DeliveryBoy/blob/master/app/src/main/res/drawable-v21/readme_b.png">

## Functionalities
- The app uses notification Services to notify the delivery boy whenever a new order is placed.
- Delivery boy can tap on each order to load specific details for the order. 
- Delivery boy can set an approximate time for the delivery which will be sent to customer via firebase.
- Delivery boy can send text notification to the customer to update them about their order.
- Delivery boy can also call the customer in case of need.
