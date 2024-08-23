USE PRJ301
GO

CREATE TABLE Department(
	departmentId CHAR(4) NOT NULL,
	deanId VARCHAR(32),
	[name] VARCHAR(32),
	PRIMARY KEY (departmentId)
)

CREATE TABLE Lecturer(
	lecturerId VARCHAR(32) NOT NULL,
	departmentId CHAR(4) NOT NULL,
	surname NVARCHAR(32),
	middleName NVARCHAR(32),
	givenName NVARCHAR(32),
	dateOfBirth DATE,
	gender BIT,
	[address] NVARCHAR(64),
	imageURL VARCHAR(256),
	email VARCHAR(64),
	recognizeDate DATE,
	PRIMARY KEY (lecturerId)
)

ALTER TABLE Department
ADD FOREIGN KEY (deanId) REFERENCES Lecturer(lecturerId);

ALTER TABLE Lecturer
ADD FOREIGN KEY (departmentId) REFERENCES Department(departmentId);

CREATE TABLE Course(
	courseId CHAR(6) NOT NULL,
	departmentId CHAR(4) NOT NULL,
	[name] VARCHAR(256),
	note VARCHAR(6),
	objective VARCHAR(256),
	resourceURL VARCHAR(256),
	prerequisite VARCHAR(32),
	credit INT,
	PRIMARY KEY (courseId),
	FOREIGN KEY (departmentId) REFERENCES Department(departmentId)
)

CREATE TABLE Major(
	majorId VARCHAR(4) NOT NULL,
	[name] VARCHAR(64),
	PRIMARY KEY (majorId)
)

CREATE TABLE Curriculum(
	majorId VARCHAR(4) NOT NULL,
	courseId CHAR(6) NOT NULL,
	termNo INT,
	PRIMARY KEY (majorId, courseId),
	FOREIGN KEY (majorId) REFERENCES Major(majorId),
	FOREIGN KEY (courseId) REFERENCES Course(courseId)
)

CREATE TABLE Student(
	studentId VARCHAR(32) NOT NULL,
	majorId VARCHAR(4) NOT NULL,
	surname NVARCHAR(32),
	middleName NVARCHAR(32),
	givenName NVARCHAR(32),
	dateOfBirth DATE,
	gender BIT,
	[address] NVARCHAR(64),
	imageURL VARCHAR(256),
	email VARCHAR(64),
	recognizeDate DATE,
	PRIMARY KEY (studentId),
	FOREIGN KEY (majorId) REFERENCES Major(majorId)
)

CREATE TABLE GradeCategory(
	categoryId VARCHAR(4) NOT NULL,
	[name] VARCHAR(32),
	categoryDescription NVARCHAR(256),
	PRIMARY KEY (categoryId)
)

CREATE TABLE GradeItem(
	itemId VARCHAR(4) NOT NULL,
	categoryId VARCHAR(4) NOT NULL, 
	itemName VARCHAR(32),
	PRIMARY KEY (itemId),
	FOREIGN KEY (categoryId) REFERENCES GradeCategory(categoryId)
)

CREATE TABLE Semester(
	semesterId INT NOT NULL IDENTITY(1,1),
	[range] VARCHAR(8) NOT NULL,
	[year] INT NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	PRIMARY KEY (semesterId),
	CHECK ([range] = 'SPRING' OR [range] = 'SUMMER' OR [range] = 'FALL')
)

CREATE TABLE Class(
	groupId CHAR(6) NOT NULL,
	courseId CHAR(6) NOT NULL,
	lecturerId VARCHAR(32) NOT NULL,
	semesterId INT NOT NULL,
	eduNextURL VARCHAR(256),
	meetURL VARCHAR(256),
	PRIMARY KEY (groupId, courseId),
	FOREIGN KEY (courseId) REFERENCES Course(courseId),
	FOREIGN KEY (lecturerId) REFERENCES Lecturer(lecturerId),
	FOREIGN KEY (semesterId) REFERENCES Semester(semesterId)
)

CREATE TABLE Exam(
	examCode VARCHAR(32) NOT NULL,
	courseId CHAR(6) NOT NULL,
	itemId VARCHAR(4) NOT NULL,
	startTime DATETIME,
	duration TIME,
	PRIMARY KEY (examCode),
	FOREIGN KEY (itemId) REFERENCES GradeItem(itemId)
)

CREATE TABLE CourseGrades(
	courseId CHAR(6) NOT NULL,
	itemId VARCHAR(4) NOT NULL,
	[weight] INT,
	criteria FLOAT,
	PRIMARY KEY (courseId, itemId),
	FOREIGN KEY (courseId) REFERENCES Course(courseId),
	FOREIGN KEY (itemId) REFERENCES GradeItem(itemId)
)

CREATE TABLE [Session](
	courseId CHAR(6) NOT NULL,
	sessionNo INT NOT NULL,
	[description] VARCHAR(256),
	PRIMARY KEY(courseId, sessionNo),
	FOREIGN KEY (courseId) REFERENCES Course(courseId)
)

CREATE TABLE TimeSlot(
	slot INT NOT NULL IDENTITY(1,1),
	startTime TIME,
	endTime TIME,
	PRIMARY KEY (slot),
)


CREATE TABLE Room(
	roomId INT NOT NULL IDENTITY(1, 1),
	building VARCHAR(16) NOT NULL,
	roomNumber INT NOT NULL,
	note VARCHAR(6),
	PRIMARY KEY (roomId)
)

CREATE TABLE CourseEnroll(
	enrollId INT NOT NULL IDENTITY(1, 1),
	studentId VARCHAR(32) NOT NULL,
	courseId CHAR(6) NOT NULL,
	groupId CHAR(6) NOT NULL,
	semesterId INT NOT NULL,
	present INT,
	average float,
	[state] VARCHAR(32),
	PRIMARY KEY (enrollId),
	FOREIGN KEY (studentId) REFERENCES Student(studentId),
	FOREIGN KEY (groupId, courseId) REFERENCES Class(groupId, courseId),
	FOREIGN KEY (semesterId) REFERENCES Semester(semesterId)
)

CREATE TABLE Grades(
	examCode VARCHAR(32) NOT NULL,
	enrollId INT NOT NULL,
	[value] FLOAT,
	note VARCHAR(32),
	PRIMARY KEY (examCode, enrollId),
	FOREIGN KEY (examCode) REFERENCES Exam(examCode),
	FOREIGN KEY (enrollId) REFERENCES CourseEnroll(enrollId)
)

CREATE TABLE [Sessions](
	groupId CHAR(6) NOT NULL,
	courseId CHAR(6) NOT NULL,
	sessionNo INT NOT NULL,
	lecturerId VARCHAR(32) NOT NULL,
	takeStatus BIT,
	roomId INT,
	slot INT,
	startDate DATE,
	PRIMARY KEY (groupId, courseId, sessionNo),
	FOREIGN KEY (groupId, courseId) REFERENCES Class(groupId, courseId),
	FOREIGN KEY (lecturerId) REFERENCES Lecturer(lecturerId),
	FOREIGN KEY (courseId, sessionNo) REFERENCES [Session](courseId, sessionNo),
	FOREIGN KEY (roomId) REFERENCES Room(roomId),
	FOREIGN KEY (slot) REFERENCES TimeSlot(slot)
)

CREATE TABLE Attends(
	enrollId INT NOT NULL,
	groupId CHAR(6) NOT NULL,
	sessionNo INT NOT NULL,
	attendStatus VARCHAR(16),
	comment VARCHAR(64),
	modifiedTime DATETIME,
	modifiedBy VARCHAR(32),
	PRIMARY KEY (enrollId, groupId, sessionNo),
	FOREIGN KEY (modifiedBy) REFERENCES Lecturer(lecturerId)
)

CREATE TABLE Account(
	username VARCHAR(32) NOT NULL,
	[password] VARCHAR(32) NOT NULL,
	googleId VARCHAR(64),
	PRIMARY KEY (username)
)

CREATE TABLE Chats(
	chatId INT NOT NULL IDENTITY(1, 1),
	groupId CHAR(6) NOT NULL,
	courseId CHAR(6) NOT NULL,
	[readonly] BIT NOT NULL DEFAULT 0,
	PRIMARY KEY(chatId),
	UNIQUE (groupId, courseId),
	FOREIGN KEY (groupId, courseId) REFERENCES Class(groupId, courseId)
)

CREATE TABLE [Messages](
	messageId INT NOT NULL IDENTITY(1, 1),
	chatId INT NOT NULL,
	sender VARCHAR(32),
	content VARCHAR(256),
	[type] INT,
	sentTime DATETIME,
	PRIMARY KEY (messageId),
	FOREIGN KEY (chatId) REFERENCES Chats(chatId),
	FOREIGN KEY (sender) REFERENCES Account(username)
)

CREATE TABLE ChatEnroll(
	chatId INT NOT NULL,
	username VARCHAR(32),
	administrator BIT,
	PRIMARY KEY (chatId, username),
	FOREIGN KEY (chatId) REFERENCES Chats(chatId),
	FOREIGN KEY (username) REFERENCES Account(username)
)

CREATE TABLE [Role](
	roleId INT NOT NULL IDENTITY(1, 1),
	[name] VARCHAR(32),
	PRIMARY KEY (roleId),
)

CREATE TABLE Permission(
	permissionId INT NOT NULL IDENTITY(1, 1),
	[name] VARCHAR(64),
	targetURL VARCHAR(64),
	PRIMARY KEY (permissionId),
)

CREATE TABLE AccountRole(
	username VARCHAR(32),
	roleId INT,
	addDate DATE DEFAULT CURRENT_TIMESTAMP,
	[expireDate] DATE,	
	PRIMARY KEY (username, roleId),
	FOREIGN KEY (username) REFERENCES Account(username),
	FOREIGN KEY (roleId) REFERENCES [Role](roleId),
)

CREATE TABLE RolePermission(
	roleId INT NOT NULL,
	permissionId INT NOT NULL,
	PRIMARY KEY (roleId, permissionId),
	FOREIGN KEY (roleId) REFERENCES [Role](roleId),
	FOREIGN KEY (permissionId) REFERENCES [Permission](permissionId),
)

CREATE TABLE Request(
	requestId INT NOT NULL IDENTITY(1, 1),
	enrollId INT NOT NULL,
	classAfter CHAR(6) NOT NULL,
	purpose VARCHAR(256),
	requestState BIT,
	response VARCHAR(256),
	staffId VARCHAR(32),
	sendTime DATETIME DEFAULT CURRENT_TIMESTAMP
	PRIMARY KEY (requestId),
	FOREIGN KEY (enrollId) REFERENCES CourseEnroll(enrollId)
)

create trigger account_generate_1
on Student
after insert
as
begin
	declare @username varchar(32);
	select @username = studentId
	from inserted;
	insert into Account(username, [password])
	values (@username, '123456a@');
	insert into AccountRole(username, roleId)
	values (@username, 1);
end

create trigger account_generate_2
on Lecturer
after insert
as
begin
	declare @username varchar(32);
	select @username = lecturerId
	from inserted;
	insert into Account(username, [password])
	values (@username, '123456a@')
	insert into AccountRole(username, roleId)
	values (@username, 2);
end

/*Add adminstrator/lecture to class's chatroom*/
create trigger chat_enroll_1
on Class
after insert
as
begin
	declare @username varchar(32);
	declare @groupId CHAR(6);
	declare @courseId CHAR(6);
	select @groupId = groupId, @courseId = courseId, @username = lecturerId
	from inserted
	insert into Chats(groupId, courseId)
	values (@groupId, @courseId)
	declare @chatId INT;
	select @chatId = chatId
	from Chats
	where groupId = @groupId AND courseId = @courseId;
	insert into ChatEnroll(chatId, username, administrator)
	values (@chatId, @username, 1);
end;

create trigger chat_enroll_2
on CourseEnroll
after insert
as
begin
	declare @username varchar(32);
	declare @groupId CHAR(6);
	declare @courseId CHAR(6);
	select @groupId = groupId, @courseId = courseId, @username = studentId
	from inserted
	declare @chatId INT;
	select @chatId = chatId
	from Chats
	where groupId = @groupId AND courseId = @courseId;
	insert into ChatEnroll(chatId, username, administrator)
	values (@chatId, @username, 0);
end;

create trigger attends_generate
on CourseEnroll
after insert
as
begin
	declare @enrollId INT;
	declare @courseId CHAR(6);
	declare @groupId CHAR(6);
	select @enrollId  = enrollId, @courseId = courseId, @groupId = groupId
	from inserted
	declare @sumSession INT;
	select @sumSession = count(distinct sessionNo)
	from [Session]
	where courseId = @courseId
	declare @count INT
	set @count = 1;
	while @count <= @sumSession
		begin
			INSERT INTO Attends(enrollId, groupId, sessionNo)
			VALUES (@enrollId, @groupId, @count);
			set @count += 1;
		end
end;

create trigger move_out_class
on Request
for update
as
begin
	declare @enrollId INT;
	declare @studentId VARCHAR(32);
	declare @courseId CHAR(6);
	declare @classBefore CHAR(6);
	declare @classAfter CHAR(6);
	declare @state BIT;
	select @enrollId = inserted.enrollId, @studentId = studentId, @courseId = courseId, @classBefore = groupId, @classAfter = classAfter, @state = requestState
	from inserted
	join CourseEnroll
	on inserted.enrollId = CourseEnroll.enrollId;
	if (@state = 1)
	begin
	if (@classBefore != @classAfter)
		begin
			update [CourseEnroll]
			set groupId = @classAfter
			where enrollId = @enrollId;
			declare @currentSession int;
			declare @currentDate date;
			select @currentDate =  CAST(GETDATE() AS DATE);
			select top(1) @currentSession = sessionNo
			from [Sessions]
			where  courseId = @courseId AND groupId =  @classBefore AND startDate <= @currentDate
			order by startDate desc;
			declare @futureSession int;
			select top(1) @futureSession = sessionNo
			from [Sessions]
			where courseId = @courseId AND groupId =  @classAfter AND startDate > @currentDate AND sessionNo >= @currentSession
			order by startDate asc;
			if @futureSession = @currentSession + 1 OR @futureSession = @currentSession
				begin
					declare @sumSession int;
					select @sumSession = count(distinct sessionNo)
					from [Session]
					where courseId = @courseId;
					while @futureSession <= @sumSession
						begin
							delete from [Attends]
							where enrollId = @enrollId AND groupId = @classBefore AND SessionNo = @futureSession
							insert into [Attends](enrollId, groupId, sessionNo)
							values (@enrollId, @classAfter, @futureSession)
							set @futureSession += 1;
						end
				end
		end
	end
end;


SELECT CourseGrades.courseId, CourseGrades.itemId, [weight], [value], note
FROM CourseGrades
LEFT JOIN CourseEnroll
ON CourseGrades.courseId = CourseEnroll.courseId
LEFT JOIN Exam
ON CourseGrades.courseId = Exam.courseId AND CourseGrades.itemId = Exam.itemId
LEFT JOIN Grades
ON CourseEnroll.enrollId = Grades.enrollId AND Exam.examCode = Grades.examCode
WHERE studentId = 'HieuTCHE170482'

SELECT CourseEnroll.courseId, Attends.groupId, startDate
FROM CourseEnroll
JOIN Attends
ON CourseEnroll.enrollId = Attends.enrollId
JOIN [Sessions]
ON CourseEnroll.courseId = [Sessions].courseId AND CourseEnroll.groupId = [Sessions].groupId AND Attends.sessionNo = [Sessions].sessionNo

CREATE TABLE Authorize(
	authId int not null identity(1, 1),
	authCode VARCHAR(16) not null,
	[for] VARCHAR(32) not null,
	[used] bit default 0,
	activeTime datetime DEFAULT CURRENT_TIMESTAMP,
	foreign key ([for]) references Account(username)
)