create databases Library;
use Library;

create table LibraryLocation(
LibraryName varchar(100) not null,
LibraryIdLocation int not null,
State varchar(100),
City varchar(100),
Country varchar(100),
Primary Key (LibraryIdLocation)
);

create table LibMember(
Member_Fname varchar(100) not null,
Member_Lname varchar(100) not null,
Member_ID int not null,
Address varchar(100),
MState varchar(100),
MCity varchar(100),
MCountry varchar(100),
MLibary int not null,
Primary Key(Member_Id),
Foreign Key (MLibary) references LibraryLocation(LibraryIdLocation)
);

create table MemberToBook(
MemberId int,
BookId int,
Due_dates varchar(100),
Total_checked_out int,
foreign key(MemberId) references LibMember(Member_ID),
foreign key(BookId) references Book(DewDecimalNum)
);
create table Book(
Category varchar(100) not null,
NameOfBook varchar(100) not null,
AuthorFirstName varchar(100) not null,
AuthorLastName varchar(100) not null,
DateOfPublication Date,
DewDecimalNum int not null,
Number_Available int,
Number_in_Total int,
LibraryIdB int not null,
Primary Key(DewDecimalNum),
Foreign Key (LibraryIdB) references LibraryLocation(LibraryIdLocation)
);

create table Employee(
Employee_Fname varchar(100) not null,
Employee_Lname varchar(100) not null,
Employee_SSN int not null,
Job varchar(100),
Hours_per_week int,
LibraryId int not null,
Foreign Key (LibraryId) references LibraryLocation(LibraryIdLocation)
);

create table OrderList(
BookName varchar(100) not null,
NumOfBookOrdered int,
AuthorFName varchar(100),
AuthorLName varchar(100),
LibraryIdO int,
EmployeeOrder int,
Primary Key(BookName),
Foreign Key (EmployeeOrder) references Employee(Employee_SSN),
FOREIGN Key (LibraryIdO) references LibraryLocation(LibraryIdLocation )
);