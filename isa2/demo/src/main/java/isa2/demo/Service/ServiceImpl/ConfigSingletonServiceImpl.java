package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.*;
import isa2.demo.Repository.ConfigSingletonRepository;
import isa2.demo.Service.ConfigSingletonService;
import isa2.demo.Service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ConfigSingletonServiceImpl implements ConfigSingletonService {

    private final ConfigSingletonRepository configSingletonRepository;

    private final UserService userService;

    public ConfigSingletonServiceImpl(ConfigSingletonRepository configSingletonRepository, UserService userService){
        this.configSingletonRepository = configSingletonRepository;
        this.userService = userService;
    }

    @Override
    public ConfigSingleton update(ConfigSingleton configSingleton) throws Exception{
        ConfigSingleton configSingleton1 = this.configSingletonRepository.findById(configSingleton.getId()).orElse(null);
        if (configSingleton1 == null){
            throw new Exception("Config with this id doesn't exist");
        }
        if (configSingleton.getGoldStartPoints() < configSingleton.getSilverStartPoints() || configSingleton.getGoldStartPoints() < configSingleton.getRegularStartPoints()){
            throw new Exception("Gold points must be greater than silver and regular points");
        }
        if (configSingleton.getSilverStartPoints() < configSingleton.getRegularStartPoints() || configSingleton.getSilverStartPoints() > configSingleton.getGoldStartPoints()){
            throw new Exception("Silver points must be greater than regular and less than gold points");
        }
        if (configSingleton.getRegularStartPoints() > configSingleton.getSilverStartPoints() || configSingleton.getRegularStartPoints() > configSingleton.getGoldStartPoints()){
            throw new Exception("Regular points must be less than silver and gold points");
        }
        
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

    @Override
    public void defineClientCategory(Client client){
        ConfigSingleton loyaltyProgram= this.getConfig(1);
            if (client.getLoyaltyPoints() == 0.0) {
                client.setCategory(UserCategory.NONE);
            } else if (client.getLoyaltyPoints() >= loyaltyProgram.getRegularStartPoints() && client.getLoyaltyPoints() < loyaltyProgram.getSilverStartPoints()) {
                client.setCategory(UserCategory.REGULAR);
            } else if (client.getLoyaltyPoints() >= loyaltyProgram.getSilverStartPoints() && client.getLoyaltyPoints() < loyaltyProgram.getGoldStartPoints()) {
                client.setCategory(UserCategory.SILVER);
            } else if (client.getLoyaltyPoints() >= loyaltyProgram.getGoldStartPoints()) {
                client.setCategory(UserCategory.GOLD);
            } else {
                client.setCategory(UserCategory.NONE);
            }
            this.userService.save(client);
    }

    @Override
    public void defineOwnerCategory(Owner owner){
        ConfigSingleton loyaltyProgram= this.getConfig(1);
        if (owner.getLoyaltyPoints() == 0.0) {
            owner.setCategory(UserCategory.NONE);
        } else if (owner.getLoyaltyPoints() >= loyaltyProgram.getRegularStartPoints() && owner.getLoyaltyPoints() < loyaltyProgram.getSilverStartPoints()) {
            owner.setCategory(UserCategory.REGULAR);
        } else if (owner.getLoyaltyPoints() >= loyaltyProgram.getSilverStartPoints() && owner.getLoyaltyPoints() < loyaltyProgram.getGoldStartPoints()) {
            owner.setCategory(UserCategory.SILVER);
        } else if (owner.getLoyaltyPoints() >= loyaltyProgram.getGoldStartPoints()) {
            owner.setCategory(UserCategory.GOLD);
        } else {
            owner.setCategory(UserCategory.NONE);
        }
        this.userService.save(owner);
    }

    @Override
    public Double getClientDiscount(Client client){
        ConfigSingleton loyaltyProgram = this.getConfig(1);
        if (client.getCategory() == UserCategory.GOLD){
            return (loyaltyProgram.getDiscountGold())/100;
        }
        if (client.getCategory() == UserCategory.SILVER){
            return (loyaltyProgram.getDiscountSilver())/100;
        }
        if (client.getCategory() == UserCategory.REGULAR){
            return (loyaltyProgram.getDiscountRegular())/100;
        }
        return -1.0;
    }

    @Override
    public Double getOwnerIncome(Owner owner){
        ConfigSingleton loyaltyProgram = this.getConfig(1);
        if (owner.getCategory() == UserCategory.GOLD){
            return (loyaltyProgram.getIncomeGold())/100;
        }
        if (owner.getCategory() == UserCategory.SILVER){
            return (loyaltyProgram.getIncomeSilver())/100;
        }
        if (owner.getCategory() == UserCategory.REGULAR){
            return (loyaltyProgram.getIncomeRegular())/100;
        }
        return -1.0;
    }
}
