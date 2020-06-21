# Orange Money Project

Se va realiza o aplicatie care va afisa cursul de schimb si va avea urmatoarele functionalitati/ecrane:

Home Screen, se vor afisa toate ratele de schimb raportate la Euro by default, intr-o lista. Se va folosi https://exchangeratesapi.io 
Ratele se vor actualiza la fiecare 3 secunde atata timp cat sunt in home screen cat si de fiecare data cand intru in ecran.
Se va afisa mereu timestamp-ul la care a fost actualizat cursul 
Ecran Istoric, se va afisa un istoric sub forma de grafic pe ultimele 10 zile pentru monedele "RON", "USD", "BGN", fiecare moneda avand graficul ei.
Ecran Setari, aici se va putea schimba moneda de baza din EUR in oricare dintre cele disponibile pe API-ul mentionat. De asemenea tot aici se va putea schimba refresh time-ul de pe home page cu urmatoarele valori posibile: 3, 5, 15


Din punct de vedere design se poate merge atat pe componente native cat si pe componente custom.
Pentru requesturi se va utiliza Alamofire.
Se va folosi design pattern-ul MVVM.
Codul trebuie sa fie documentat.
Modelele aplicatiei trebuie sa fie testate prin Unit Teste.
Se va furniza spre review Orange intreg proiectul cu codul sursa si forma binara.
Codul sursa va fi comis in https://bitbucket.org/ sau https://github.com  
