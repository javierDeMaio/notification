# notification
challenge for modak

Endpoint:
(POST) localhost:8080/api/notification/send

Request(EXAMPLE): 
{
    "content":"HELLO",
    "title":"WORD",
    "recipient":"JAVIERDEMAIO1@GMAIL.COM",
    "dateSent":"2024-09-05T17:54:55",
    "notificationType":{
        "type":"News",
     "intervalInSeconds":30
    }
}

Responses:
200 OK: The notification was successfully sent.
500 Internal Server Error: Could not send more Notifications "Recipient" Exceded Quantity of Requests

500 Internal Server Error: An error occurred while sending the notification.
