# Ekip Üyeleri
22060349 -> Furkan Ece

22060377 -> Enes Kaan Afacan

22060364 -> Emir Köse

22060347 -> Baran Ar

## Örnek Akış

Server1, Server2 ve Server3 ayrı ayrı koşturulur.Her serverın Admin ve Clientlar için ayrı portu vardır. Serverlar dinleme halindeyken AdminClient.java çalışmaya başlar. Admin her servera o serverın admin portu üzerinden bağlanır. Adminin demandları için bir arayüz bulunur. arayüzde SUBS, CPCTY, STRT ve EXIT komutları bulunur.Farklı bir demand yazılması durumunda admin ve serverlar WRONG DEMAND çıktısı verir. Admin STRT komutu vererek serberların abone bilgisini alması işlemini başlatır. STRT komutunu alan serverlar YEP cevabını yollar. Clientlar abone bilgisini ilgili serverın client portuna yollar. Admin abone bilgisi alındıktan sonra admin SUBS ile serverlardaki abone bilgisini yazdırabilir veya CPCTY ile her serverda bulunan abone sayısını alabilir. EXIT komutu admin arayüzünü kapatır ve bağlantıyı keser.

## Sistemin Giriş Ekranı
![Ekran görüntüsü 2025-01-10 093817](https://github.com/user-attachments/assets/c017e56f-ca9c-4d98-9004-ca04dbe6d867) ![Ekran görüntüsü 2025-01-10 093831](https://github.com/user-attachments/assets/5ec32c4f-290d-411b-99fb-3ef415087648)

## CPCTY Komutunun İşlenmesi
![Ekran görüntüsü 2025-01-10 094156](https://github.com/user-attachments/assets/f7cac6c3-3ba7-44b4-97bb-811c58c4f00e)![Ekran görüntüsü 2025-01-10 094436](https://github.com/user-attachments/assets/cc973425-ed37-467f-a8f8-f510e647842d)

## SUBS Komutunun İşlenmesi
![Ekran görüntüsü 2025-01-10 094448](https://github.com/user-attachments/assets/de21fada-8e82-414b-9368-b505e225445b)



## ServerX.java özellikleri
- AdminClient.java ile başlar.
- Her serverın client ve admin portu vardır.
- 


## AdminClient.java özellikleri
- Admin komutları için arayüz bulundurur.
- Her bir server için ayrı port bulundurur.

## Özet
- Java kullanılarak admin kontolünde çalışan; farklı clientlardan abone bilgileri alan ve bu bilgileri kaydeden serverlar tasarlanmıştır. Bu serverlar admin arayüzünden yollanan komutları alır ve işler. İşlenen komutlara uygun cevaplar admine yollanır. Admin bu cevapları alarak yazdırır.


## Sunum Videosu Linki
