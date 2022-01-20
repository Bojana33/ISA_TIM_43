package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.ConfigSingleton;
import isa2.demo.Repository.ConfigSingletonRepository;
import isa2.demo.Service.ConfigSingletonService;
import org.springframework.stereotype.Service;

@Service
public class ConfigSingletonServiceImpl implements ConfigSingletonService {

    private final ConfigSingletonRepository configSingletonRepository;

    public ConfigSingletonServiceImpl(ConfigSingletonRepository configSingletonRepository){
        this.configSingletonRepository = configSingletonRepository;
    }

    @Override
    public ConfigSingleton update(ConfigSingleton configSingleton) {
        ConfigSingleton configSingleton1 = this.configSingletonRepository.findById(configSingleton.getId()).orElse(null);
        configSingleton1.setClientReservationPoints(configSingleton.getClientReservationPoints());
        configSingleton1.setDiscountGold(configSingleton.getDiscountGold());
        configSingleton1.setDiscountRegular(configSingleton.getDiscountRegular());
        configSingleton1.setDiscountSilver(configSingleton.getDiscountSilver());
        configSingleton1.setFeePercentage(configSingleton.getFeePercentage());
        configSingleton1.setGoldStartPoints(configSingleton.getGoldStartPoints());
        configSingleton1.setRegularStartPoints(configSingleton.getRegularStartPoints());
        configSingleton1.setSilverStartPoints(configSingleton.getSilverStartPoints());
        configSingleton1.setIncomeGold(configSingleton.getIncomeGold());
        configSingleton1.setIncomeRegular(configSingleton.getIncomeRegular());
        configSingleton1.setIncomeSilver(configSingleton.getIncomeSilver());
        configSingleton1.setSuccessfulOwnerPoints(configSingleton.getSuccessfulOwnerPoints());
        return this.configSingletonRepository.save(configSingleton1);
    }

    @Override
    public ConfigSingleton getConfig(Integer id) {
        return this.configSingletonRepository.findById(id).orElse(null);
    }
}
