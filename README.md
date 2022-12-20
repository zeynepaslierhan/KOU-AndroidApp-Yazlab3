# Hafıza Oynu

Bu proje ile Android uygulama ve bulut bilişim teknolojilerinin kullanılması amaçlanmaktadır. Projede belirtilen süre içinde zorluk seviyesine göre kartları doğru eşleştirmek gerekmektedir. 

***Programlama Dili:*** Kotlin

***Geliştme Ortamı *(IDE)*:*** Android Studio

***Bulut Bilişim *(Cloud)*:*** Firebase

## Uygulamanın Arayüzleri

* **Giriş ekranı:** Oyun ilk açıldığında ekranda açılacak sayfa giriş ekranı vardır. Kullanıcı bu ekrandan, kullanıcı adı ve şifresi ile giriş yapabilir, şifre değiştirebilir ve kaydolabilir.

  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/Login-Register.png" height="200">

* **Oyun ekranı:** Kullanıcı giriş yaptıktan sonra karşısına gelecek olan ekran, oyun ekranıdır. Burada Tek Oyuncu ve Çoklu Oyuncu Olarak iki farklı seçenek bulunur. Oyun ekranı ilk açıldığında “BAŞLA” butonu bulunur. Oyuncu BAŞLA butonuna tıkladıktan 3 saniye sonra oyun ve süre başlatılır.

  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/OyuncuSec.png" align ="left" height="200">

  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/ZorlukSec.png" align ="left" height="200">

  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/Loading.png" align ="left" height="200">

</br>
</br>
</br>
</br>
</br>
</br>
</br>
</br>
</br>

* **Oyun zorluk seviyesi:** Oyunda 2x2, 4x4 ve 6x6 olmak üzere 3 farklı zorluk seviyesi vardır. Oyun başlatıldığında kartlar kapalı şekilde dağıtılır. Oyundaki kartların her birinden birer çift bulur. Buradaki amaç açılan kartın diğer çiftini bulabilmektir. Oyunda kartların sırası her seferinde rastgeledir.

  | Oyuncu | Basit | Orta | Zor | 
  | :---         |     :---:      |          ---: |         ---: |
  | *Çoklu Oyuncu*   | <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/MultiBasit.png" height="200">   | <img   src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/MultiOrta.png" height="200">    | <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/MultiZor.png" height="200">    
  | *Tek Kişilik*   |  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/TekKisilikBasit.png" height="200">         |  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/TekKisilikOrta.png" height="200">       |  <img src="https://github.com/zeynepaslierhan/KOU-AndroidApp-Yazlab3/blob/main/img/Aray%C3%BCz/TekKisilikZor.png" height="200">       |


### Müzikler

Arka plan müziği: Oyun esnasında arka planda bir müzik çalınır. Bu müzik oyun oynanırken çalmaya devam eder ve 3 durumda bu müzik değişecektir. 

1. Kartın eşi bulunduğunda farklı bir müzik ile uyarı verilir. 

2. Oyun süresi bittiği zaman arka fon müziği olumsuz bir uyarı verilir. 

3. Süre bitmeden bütün kartların eşi bulununca arka fon müziği kazandınız anlamında olumlu bir uyarı verir.


## Kaynakça
1. [Kotlin İle Android Mobil Uygulama Geliştirme Eğitimi Temel Seviye , Atıl Samancıoğlu, *BTK Akademi*](https://www.btkakademi.gov.tr/portal/course/kotlin-ile-android-mobil-uygulama-gelistirme-egitimi-temel-seviye-10274)
1. [Kotlin Tutorial](https://www.w3schools.com/KOTLIN/index.php)
1. [Android Tutorial](https://www.tutorialspoint.com/android/index.htm)
1. [Nedir bu Android Service ve Broadcast Receiver](https://medium.com/kodluyoruz/nedir-bu-android-service-ve-broadcast-receiver-291168de075b)
1. [Developer Guides | Android Developers](https://developer.android.com/guide)
1. [Kotlin İle Android Mobil Uygulama Geliştirme İleri Seviye, Atıl Samancıoğlu, *BTK Akademi*](https://www.btkakademi.gov.tr/portal/course/kotlin-ile-android-mobil-uygulama-gelistirme-ileri-seviye-10359)
1. [Firebase Docs](https://firebase.google.com/docs/android/setup#kotlin+ktx_2)
1. [İstenilen aktivitenin çalıştırılması veya ana erkana çağrılması](https://stackoverflow.com/questions/38308161/how-to-run-a-certain-activity-in-android-studio)
1. [How to Support ALL Screen Sizes on Android](https://www.youtube.com/watch?v=5lSQcJjZPFs)
2. [How to Retrieve Image from Firebase in Realtime in Android?](https://www.geeksforgeeks.org/how-to-retrieve-image-from-firebase-in-realtime-in-android/)
3. [Play Audio Song firebase Storage in Android Studio Tutorial](https://www.youtube.com/watch?v=DxZMDSKNG1A)
4. [Build a Tic Tac Toe Game in Android Studio - Online Mode | GeeksforGeeks](https://www.youtube.com/watch?v=-GmU5fSyeV0)



