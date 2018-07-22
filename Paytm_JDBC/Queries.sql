create table Customer(name varchar2(10) not null,
                   mobileNo varchar2(10) not null,
                   wallet number(8,2) not null,
                   primary key(mobileNo));
                   
create table Wallet(balance number(10,2));  

  
create table Customers(name varchar2(10) not null,
                   mobileNo varchar2(10) not null,
                   balance number(10,2) not null,
                   primary key(mobileNo));             