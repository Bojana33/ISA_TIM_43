package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.ConfigSingleton;
import isa2.demo.Model.Owner;

public interface ConfigSingletonService {

    ConfigSingleton update(ConfigSingleton configSingleton) throws Exception;

    ConfigSingleton getConfig(Integer id);

    void defineClientCategory(Client client);

    void defineOwnerCategory(Owner owner);

    Double getClientDiscount(Client client);

    Double getOwnerIncome(Owner owner);

    void addReservationPointsToClient(Client client);

    void addReservationPointsToOwner(Owner owner);


}
