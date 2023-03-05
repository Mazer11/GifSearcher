# GifSearcher

Данное приложение создано в рамках одного этапа отбора на Internship.
Приложение использует API от https://giphy.com/
Реализовано 2 экрана в соответствии с требованиями (все скриншоты с подписями ниже):
<ol>
<li>Первый экран - список с возможностью поиска и отображения 25 гифок. В соответствии с дополнительными и не обязательными требованиями, была реализована пагинация.</li>
<li>Второй экран - экран с информацией о гифке. Отображается сама гифка, её название, автор, дата импорта на сервер, вес, исходный масштаб.</li>
</ol>
Кроме вышеуказанного, реализована возможность смены цветовой темы приложения. Это тёмная и светлая темы с классическими цветами от Google.
Если верить комитам, то срок написания кода приложения (не считая время затраченное на данный README.md) представлен следующим интервалом:</br>
03.03.23 4:09 PM GMT+3 - 05.03.23 6:40 AM GMT+3
</br>
Стек:</br>
<ol>
  <li>Kotlin</li>
  <li>Coil - для асинхронной загрузки и отображения Gif. Glide с Compose не очень дружит.</li>
  <li>Okhttp + Retrofit + Moshi(для JSON-ов) - запросы в сеть.</li>
  <li>Coroutines</li>
  <li>Jetpack Compose</li>
  <li>Hilt - внедрял зависимости.</li>
  <li>DataStore - для хранения установленной цветовой темы приложения.</li>
  <li>Material 3 - некоторые composable функции.</li>
</ol>
Все скриншоты сделаны на смартфоне Huawei P40 Lite на базе Android SDK 29 без Google Services.
Изображения сжимал в Paint (так было быстрее).
</br>
</br>Первый экран при запуске приложения (В двух цветовых темах).</br>
Книжная ориентация:</br>
</br>

![photo_6_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937544-7b450e1d-08df-45e2-a9cf-2a0e87486815.jpg)
![photo_4_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937540-8f685d55-d8c9-4c2f-8629-74f41171c0e2.jpg)
![photo_10_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937549-78b7ec2d-82e8-4faa-b1e8-cd80b14041c9.jpg)
</br>
</br>Альбомная ориентация:</br>
</br>
![photo_3_2023-03-05_05-17-17](https://user-images.githubusercontent.com/86118013/222938203-71d63f32-bce3-46e5-a8e5-e2dec6b40442.jpg)
![photo_5_2023-03-05_05-17-17](https://user-images.githubusercontent.com/86118013/222938205-5b21017e-7756-408c-8c07-5a9c72e0cfcf.jpg)
</br>
</br>Первый экран при возникновении ошибки при отправке запроса. Например, отсутствие интернета.</br>
</br>
![photo_2023-03-05_06-43-07](https://user-images.githubusercontent.com/86118013/222940492-69f32a76-2c2c-4a87-a549-a1ff954079fc.jpg)
</br>
</br>Первый экран при использовании пустого списка. Например, если пришёл пустой ответ с сервера при поиске. </br>
</br>
![photo_13_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937552-5ec7fb5d-8a84-4c7f-93e5-df37583002d4.jpg)
</br>
</br>Загрузка на первом экране при первом запуске. </br>
</br>
![photo_3_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937536-704070ff-6219-422a-b749-d0b63b4831ad.jpg)
</br>
</br>Результат поиска гифок по запросу.</br>
</br>
![photo_7_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937545-27ee0590-5eda-4774-8e1b-9a98a94a228f.jpg)
![photo_1_2023-03-05_05-17-17](https://user-images.githubusercontent.com/86118013/222938200-a8ebd702-b260-47dc-9feb-7dd33c09720a.jpg)
</br>
</br>Второй экран. В обоих цветовых темах (светлая и тёмная) и в обеих ориентациях.</br>
</br>
![photo_2_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937385-28ce4643-3a6d-4972-a117-5a6d5b9b3302.jpg)
![photo_5_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937541-6bc80d51-6b05-4c1a-a9d4-5bb449fb920a.jpg)
![photo_9_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937547-058cfbb3-7245-4a5e-acac-6cd7992278d3.jpg)
![photo_4_2023-03-05_05-17-17](https://user-images.githubusercontent.com/86118013/222938204-54857421-f2da-467b-8455-4cc50be7f946.jpg)
</br>
</br>Экраны при пролистывании текущего списка до конца. После прогрузки появятся ещё гифки. Пагинация.</br>
</br>
![photo_8_2023-03-05_04-49-49](https://user-images.githubusercontent.com/86118013/222937546-ef2ef5c2-ac4e-46c3-ab0f-f5ed80e93df7.jpg)
![photo_2_2023-03-05_05-17-17](https://user-images.githubusercontent.com/86118013/222938202-39f27ff0-3be7-4c7b-af74-329265639aed.jpg)
</br>
</br>
Немного подробнее про мой опыт можно узнать из этого резюме:</br>
Ссылка на первый проект из резюме: https://github.com/Mazer11/Recommendaily_client </br>
Ссылка на второй проект из резюме: https://github.com/Mazer11/RestorauntRecords_compose </br></br>
![Mazurak_Sergey_CV](https://user-images.githubusercontent.com/86118013/222939578-f0b1a057-ddc2-492c-b21f-6fd235a3155b.jpg)


