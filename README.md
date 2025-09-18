Działanie algorytmu sterowania

Skrzyżowanie podzielone jest na:
- 4 drogi
- Każda droga posiada w sobie pas - zamysł jest taki by finalnie umożliwić konfigurację liczby pasów
- Każdy pas (Lane) posiada swoje światła drogowe oraz kolejkę pojazdów
- Światła drogowe łączone są w grupy niekolidujące, które w danym momencie mogą mieć taki sam stan. Aktualnie jest to rozwiązane poprzez ręczne ustalenie konfiguracji w jakiej chcemy uzyskać skrzyżowanie, w samej implementacji projektu starałem się jednak pozostawić dowolność co do wyboru ilości grup. Poświęciłem czas nad algorytmem dynamicznie tworzącym grupy, jednak z uwagi na specyfikę problemu - wiele pasów, różna możliwości skrętów dla danych pasów itd. problem jest z zakresu NP-trudnych, można uzyskać rozwiązanie korzystając z grafów kolorowanych, jednak z racji samej specyfki zadania nie chciałem wchodzić w takie szczególy :)

Symulacja działa na zasadzie: wrzucamy config -> symulowane są stany i wyrzucany jest plik z wynikiem. 

Plik wejściowy, jak i wyjściowy jest parametryzowany, nie miałem jednak okazji przeprowadzenia tutaj testów różnych scenariuszy. Co ważne, aktualna implementacja oczekuja formatu: --input-file inputFile --output-file outputFile, w odwrotnej kolejności inputFile zostanie nadpisany. 

W aktualnej wersji programu, symulacja nie uwzględnia kolizji dla pojazdów przecinających pas przeciwny, gdzie w obecnej konfiguracji światło należy do tej samej grupy sygnalizatorów.

Algorytm doboru strategii sterowania sygnalizatorami działa na zasadzie plug & play, w zależności od ustalonej heurystyki. W obecnej wersji jest to liczba pojazdów w kolejce dla danej grupy sygnalizatorów. Jeśli przekracza ona o 20% średnią(idealną) liczbę pojazdów oraz mamy przynajmniej 10 pojazdów na skrzyżowaniu w kolejkach, dokonuje się zamiana algorytmu na adaptacyjny. Taka sytuacja oznacza brak balansu między grupami sygnalizatorów. W zamyśle algorytm adaptacyjny ma polegać na przydzielaniu każdej grupie sygnalizatorów indywidualnego czasu zielonego światła zgodnie z proporcjami (wyliczonego pressure). Jeśli 80% pojazdów znajdujących się na skrzyżowaniu należy do jednej grupy sygnalizatorów, należy przydzielić 80% dostępnego czasu zielonego właśnie tej grupie, respektując przy tym maksymalny czas trwania dla światła zielonego oraz minimalny dla pozostałych grup, o mniejszym natężeniu. Niestety z racji braku czasu na implementację algorytmu, nie jest on zaimplementowany, choć projekt jest przygotowany na dodawanie strategii zarządzania światłami poprzez wstrzykiwanie ich. 

Wiele jest do poprawy w symulacji, jednak samo zadanie znacząco wykracza poza zakres zadania domowego do wykonania w jeden wieczór, stąd pewne niedociągnięcia. Mimo tego zamysł projektu jest ciekawy i zdecydowanie daje możliwości rozbudowywania o kolejne komponenty.
