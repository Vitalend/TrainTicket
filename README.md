Микросервисное  API которое реализует покупку билетов на поезд. Состоит из контроллеров, сервисов и моделей, так же есть пакет util, 
в котором находятся исключения и класс для подключения к базе данных. Приложение принимает на вход Json и отдает так же Json, работает с БД Postgresql.

В приложении реализован следующий функционал:

1.	Отправив GET запрос на адрес http://localhost:6060/train/all приложение отдаст список всех доступных поездов.

2.	Отправив PATCH запрос на адрес http://localhost:6060/ticket/buy/one  и передав Json
   
{
   
    "trainNumber": номер поезда,
  
    "wagonNumber": номер вагона,
  
    "seatNumber":  номер места
   
}

 приложение пометит билет в БД как купленный и вернет сообщение об успешной покупке, либо если введены некорректные данные или
билет уже был куплен ранее – вернет сообщение об ошибке.

3.	Отправив PATCH запрос на адрес http://localhost:6060/ticket/buy/group  и передав Json
   
{
  
    "trainNumber": номер поезда,
   
    "wagonNumber": номер вагона,

    "ticketQuantity": количество билетов
}

приложение пометит выбранное количество билетов в БД как купленные и вернет сообщение об успешной покупке,
 либо если введены некорректные данные или количества свободных билетов нехватает – вернет сообщение об ошибке.

4.	Отправив PATCH запрос на адрес http://localhost:6060/ticket/refund  и передав Json
   
{
   
    "trainNumber": номер поезда,   

    "wagonNumber": номер вагона,

    "seatNumber":  номер места
   
} 
   
приложение пометит билет в БД как доступный к покупке и вернет сообщение об успешном возврате, либо если введены некорректные данные,
билет ранее не был куплен или возврат происходит менее чем за 2 часа до отправления – вернет сообщение об ошибке.

Приложение работает  с таблицими, которые созданы следующими sql командами:

CREATE TABLE Trains

(

    train_id        int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    
    train_number    int          NOT NULL,
    
    train_route     varchar(100) NOT NULL,
    
    train_departure timestamp  NOT NULL
    
);



CREATE TABLE Wagons

(

    wagon_id     int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    
    train_id     int NOT NULL REFERENCES trains(train_id) ON DELETE CASCADE,
    
    wagon_number int NOT NULL 
    
);



CREATE TABLE Seats

(

    seat_id     int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    
    wagon_id    int REFERENCES Wagons (wagon_id) ON DELETE CASCADE,
    
    seat_number int     NOT NULL,
    
    seat_buying boolean NOT NULL
    
);




