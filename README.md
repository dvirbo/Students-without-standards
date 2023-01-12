
# Students without standards 

## A new platform for sharing products between students



![swsLogo](https://user-images.githubusercontent.com/73783656/208363534-da4efae6-d514-49df-8507-777ae56aa2e8.PNG)


## Authors

- [@dvir borochov](https://www.github.com/dvirbo)
- [@ohad maday](https://www.github.com/Ohad-Ma)
- [@Denis Kostyuk](https://www.github.com/DenisKostyuk)
## About

A standard student has two basic problems:
lack of time and lack of cash.
The platform is designed to enable the student on these two levels, and the student will be able to receive (and donate) products at no cost, and also Save transportation and search time.

We hope this project will make it easier for the students and bring them to better academic performance.



# Main features
- Registration - administrator or regular user
- login
- Admin functions (admin panel) - deleting all ads, deleting comments in ads to prevent spam messages
- search post in desire institution
- Posting an ad
- “like” button 
- Comments in posts


# Three-tier architecture

## Presentation Tier

- Login panel
- RegisLoginActivityterActivity – Register Panel	
- MainActivity – Main menu (after log-in/register)
- PostActivity – Write a post
- searchPostActivity – Search a post
- CommentsActivity – Write a comment on a post
- InfoActivity – Information about the application
- InstitutionsActivity – The board of each University
- UniversitiesActivity – Which University to choose

## Business Tier 

- CommentAdapter – Connects between RealTime DataBase 
(Data tier) and Comments activity which is in the Presentation Tier.
- PostAdapter – Connects between FireStore database
 (Data tier) and Institutions Activity which is in the Presentation Tier.


## Data Tier
- FireStore Database 
- RealTime Database

# MVC 

![readme_pic_1](https://user-images.githubusercontent.com/73783656/212079603-428bd336-adac-4fd9-999b-cdbbe975b04e.png)





