********************************************** KYRIAKOS ALEXANDROU **********************************************
22/01/2016 MY PERSONAL NOTES:
*****************************************************************************************************************
Retrieving images from the server issue (not sure if this is how it should have been or if it was a trick or if it was a bug)
When an image is not found from the server then it returns an empty list, however when an image is found then an object is returned instead of an array.
The following error will happen when you try to serialize the json data to a wrong variable type. It makes sense, you can't set an array to an object or vice versa.
com.google.gson.JsonSyntaxException: java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 5855 path $.data[10].images

example:
IMAGES FOUND json response
      "images": {
        "750": {
          "url": "https:\/\/d1zy6g4gck72w2.cloudfront.net\/cms\/product-image-landscape\/AP-1FCD-DES-02-P-x750.jpg",
          "width": 750
        }
      }
IMAGES NOT FOUND json response
"images": []

currently I have the app to only accept a list of images. Therefore, if an image object is actually retrieved instead of an array it will crash


*****************************************************************************************************************
The following libraries where used:
-retrofit 1.9.0
-gson 2.2.4
-eventbus 2.4.0
-picasso 2.4.0
*****************************************************************************************************************
known issues:
-choosing a product to see the details and then navigating back to the first screen causes the app to re-fetch the
data from the server and eventually refreshing the state of all the views to the start
*****************************************************************************************************************
Future improvements that would have been nice:
- cache all the products and categories objects into an SQLite(could use greenDAO etc.).
- UI styling. Use recycleView to make product images to scroll horizontally when viewing the full details of a product
- swipe between products when looking at the full details of a product
*****************************************************************************************************************



Mobile Developer Test

We would like to ask you to create a simple project in a platform specific IDE (Xcode
for iOS, Android Studio for Android) and implement the following functionality.

The task will be to design and implement a standalone app that lists and displays
Gousto’s additional products.
Customer adds additional products after selecting recipes and placing a box order.
Examples of additional products: wine, kitchen utensils, desserts.

Please have a look at two API endpoints:
https://api.gousto.co.uk/products/v2.0/products
Returns a list of additional products available.

https://api.gousto.co.uk/products/v2.0/categories
Returns a list of categories for additional products.

Example:
https://api.gousto.co.uk/products/v2.0/products?includes[]=categories&includes[]=attr
ibutes&image_sizes[]=750

- "image_sizes" array specifies that the returned object will include images with
widths of requested sizes (or the closest bigger size than requested); that
allows to optimize the image sizes for certain mobile devices.
- "includes" array specifies additional objects to be returned with products, for
example "categories", "attributes"

Functional Requirements
● Product list should display product title, price, image (please feel free to show
any additional info returned by the API).
● There should be an option to filter products by a category. (with an “All
Categories” option)
● Please feel free to come up with your own design of the screens.

Non Functional Requirements
● The app should be built with good UX and use native UI elements for the
platform (Android, iOS) where possible.
● It is allowed to use any required thirdparty
libraries.
● Please create some unit tests as part of the task.

Extra Brownie Points For (not required):
● Create a simple user automated test for the application (for example switching
the selected category)
● Create a Product Detail page with some transition between the screens
● Provide landscape support (with some UI adjustment, for example grid
instead of the list in the landscape mode etc.)