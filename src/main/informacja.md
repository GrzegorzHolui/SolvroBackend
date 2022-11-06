W paczce config jest klasa DataBaseItemConfiguration
w niej są ustalone produkty które można dodać do koszyka oraz rabaty jakie można użyć.

Dostępne do dodania przedmioty to:

- laptop cena 10 ,produktHash: laptopHash
- torch cena to 1 ,produktHash: torchHash
- pc cena to 10 ,produktHash: pcHash

Dostępne do dodania rabaty to:

- laptopCard, cardHash: "laptopCard"
- pcCard, cardHash: pcHash

Jakby co to przedmioty oraz rabaty znajdują się w pliku DataBaseItemConfiguration

Sposoby dostawy to:

- COURIER_DELIVERY_INPOST cena to 20
- PACKAGE_LOCKER_INPOST cena to 10
- MAIL_PARCEL_POCZTA_POLSKA cena to 5

W pliku api-spec.yml jest specyfikacja endpointów