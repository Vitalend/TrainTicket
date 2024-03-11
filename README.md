Микросервисное  API которое реализует покупку билетов на поезд. Состоит из контроллеров, сервисов и моделей, так же есть пакет util, 
в котором находятся исключения и класс для подключения к базе данных. Приложение принимает на вход Json и отдает так же Json. 

В приложении реализован следующий функционал:

1.	Отправив GET запрос на адрес http://localhost:6060/trainDTO/all приложение отдаст список всех доступных поездов .

2.	Отправив PATCH запрос на адрес http://localhost:6060/ticket/buy/one  и передав Json 
{
   "trainNumber": номер поезда,
   "wagonNumber": номер вагона,
   "seatNumber":  номер места
}
 приложение пометит билет в БД как купленный и вернет сообщение об успешной покупке, либо если введены некорректные данные или
билет уже был куплен ранее – вернет сообщение об ошибке.

4.	Отправив PATCH запрос на адрес http://localhost:6060/ticket/buy/group  и передав Json 
{
    "trainNumber": номер поезда,
    "wagonNumber": номер вагона,

    "ticketQuantity": количество билетов
}
приложение пометит выбранное количество билето в БД как купленные и вернет сообщение об успешной покупке,
 либо если введены некорректные данные или количества свободных билетов нехватает – вернет сообщение об ошибке.

6.	Отправив PATCH запрос на адрес http://localhost:6060/ticket/refund  и передав Json 
   {
   "trainNumber": номер поезда,

"wagonNumber": номер вагона,

   "seatNumber":  номер места
   } 
приложение пометит билет в БД как доступный к покупке и вернет сообщение об успешном возврате, либо если введены некорректные данные 
или билет ранее не был куплен – вернет сообщение об ошибке.
