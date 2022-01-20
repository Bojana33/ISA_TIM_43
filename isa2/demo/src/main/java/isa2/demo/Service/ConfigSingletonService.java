package isa2.demo.Service;

import isa2.demo.Model.ConfigSingleton;

public interface ConfigSingletonService {

    ConfigSingleton update(ConfigSingleton configSingleton);

    ConfigSingleton getConfig(Integer id);
}
