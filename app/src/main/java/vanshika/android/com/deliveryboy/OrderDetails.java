package vanshika.android.com.deliveryboy;

public class OrderDetails {
  private String name;
  private String email;
  private String mobile;
  private String address;
  private String restaurant;
  private String summary;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  private String token;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  String orderId;

  public OrderDetails(){
    //emty constructor required for firebase
  }

  public OrderDetails(String name, String email, String mobile, String address,
      String restaurant, String summary, String token, String orderId) {
    this.name = name;
    this.email = email;
    this.mobile = mobile;
    this.address = address;
    this.restaurant = restaurant;
    this.summary = summary;
    this.token = token;
    this.orderId = orderId;
  }

  //public OrderDetails(String name, String email, String mobile, String address,
  //    String restaurant, String summary) {
  //  this.name = name;
  //  this.email = email;
  //  this.mobile = mobile;
  //  this.address = address;
  //  this.restaurant = restaurant;
  //  this.summary = summary;
  //}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(String restaurant) {
    this.restaurant = restaurant;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
