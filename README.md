# Ekip Üyeleri
22060349 -> Furkan Ece

22060377 -> Enes Kaan Afacan

22060364 -> Emir Köse

22060347 -> Baran Ar

## Örnek Akış

Server1, Server2 ve Server3 ayrı ayrı koşturulur.Her serverın Admin ve Clientlar için ayrı portu vardır. Serverlar dinleme halindeyken AdminClient.java çalışmaya başlar. Admin her servera o serverın admin portu üzerinden bağlanır. Adminin demandları için bir arayüz bulunur. arayüzde SUBS, CPCTY, STRT ve EXIT komutları bulunur.Farklı bir demand yazılması durumunda admin ve serverlar WRONG DEMAND çıktısı verir. Admin STRT komutu vererek serberların abone bilgisini alması işlemini başlatır. STRT komutunu alan serverlar YEP cevabını yollar. Clientlar abone bilgisini ilgili serverın client portuna yollar. Admin abone bilgisi alındıktan sonra admin SUBS ile serverlardaki abone bilgisini yazdırabilir veya CPCTY ile her serverda bulunan abone sayısını alabilir. EXIT komutu admin arayüzünü kapatır ve bağlantıyı keser.

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
