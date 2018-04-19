package database.factory;

public class FactoryProducer {

    private FactoryProducer() {

    }

    public static Factory getFactory(String factory) {
        if(factory == null) {
            return null;
        }

        if(factory.equalsIgnoreCase("DAO")) {
            return new DAOFactory();
        }

        if(factory.equalsIgnoreCase("HIBERNATE")) {
            return new HibernateFactory();
        }

        return null;
    }
}
