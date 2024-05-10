# notification
# challenge for modak

# After clonning the repository, On bash or CMD:
# -cd notification(Make sure to be on the source of the proyect)
# -run "gradle build"
# -Go to "NotificationAplication", Right click on the class and run the app.

# Try it out on Postman:

# Endpoint

(POST) localhost:8080/api/notification/send
# Request(EXAMPLE): 
{
    "recipient":"JAVIERDEMAIO1@GMAIL.COM"
    "dateSent":"2024-09-05T17:54:55",
    "type":"News"
}
# Responses:
 200 OK: The notification was successfully sent.
 429 Too many requests: Could not send more Notifications "Recipient" Exceded Quantity of Requests.
 500 Internal Server Error: An error occurred while sending the notification.
