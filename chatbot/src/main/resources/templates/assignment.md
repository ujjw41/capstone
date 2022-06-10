CAPSTONE PROJECT
================================
Title : Create a Chatbot for a Website.

Short Description:
Create a Website which will have a basic chatbox. Any person can start chatting with the
website chatbot. If a person is a registered user of a website, he can access his profile info as well via chat.

Website owner has an admin dashboard where he can see all the chats. Which are the questions the chatbot couldn't answer. He can view and add those questions to the database so that those questions will be answered by the chatbot in future.

Tasks

Build a website with basic chatbot and user registration / login and profile. User is a university student, so you can create a user profile accordingly.
Website will have an admin panel as well. Admin will be able to see all the chats. Every chat session should be assigned a unique ID. Admin can access all the chats or delete with the ID. if he wants he can view entire conversion as well
Also the admin should be able to edit questions and answers available in the database. Create or edit options should be available as well.
All communication between chatbox and server will happen through AJAX. If the user is logged in then only profile related questions will be answered. So user specific APIs should be secured.
50% of the weightage will be given to finding the appropriate answer for the question in the database. You can start with exact matching. Then Regex/Partial matching.

Create restful APIs to access conversations and chats. All APIs should be secured with JWT token. User will be able to access his own resources. Admin can access all the resources. Create Swagger URL for all APIs as well unit test cases.

Architecture Guideline:

Minimum Microservices:
API Gateway:
Eureka Server Registry:
Website Service
API Service

Database Tables:
User Table
QnA Table
User Info Table
Conversations Table → List of all chats
Chats Table → All the messages from both user as well as chatbot
