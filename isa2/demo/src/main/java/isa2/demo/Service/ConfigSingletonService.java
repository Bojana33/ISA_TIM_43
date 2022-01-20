package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.ConfigSingleton;
import isa2.demo.Model.Owner;

public interface ConfigSingletonService {

    ConfigSingleton update(ConfigSingleton configSingleton) throws Exception;

    ConfigSingleton getConfig(Integer id);

    public void defineClientCategory(Client client);

    public void defineOwnerCategory(Owner owner);

    public Double getClientDiscount(Client client);

    public Double getOwnerIncome(Owner owner);
}
