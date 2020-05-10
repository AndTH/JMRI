package jmri.jmrix.mqtt;

import java.util.Comparator;
import java.util.ResourceBundle;
import jmri.InstanceManager;
import jmri.NamedBean;
import jmri.jmrix.SystemConnectionMemo;
import jmri.util.NamedBeanComparator;

/**
 *
 * @author Lionel Jeanson
 */
public class MqttSystemConnectionMemo extends SystemConnectionMemo {

    private MqttAdapter mqttAdapter;

    public MqttSystemConnectionMemo() {
        super("M", "MQTT");
        register();
        InstanceManager.store(this, MqttSystemConnectionMemo.class);
    }

    public void configureManagers() {
//        setPowerManager(new jmri.jmrix.jmriclient.JMRIClientPowerManager(this));
//        jmri.InstanceManager.store(getPowerManager(), jmri.PowerManager.class);
        InstanceManager.setTurnoutManager(getTurnoutManager());
        InstanceManager.setSensorManager(getSensorManager());
//        jmri.InstanceManager.setLightManager(getLightManager());
//        jmri.InstanceManager.setReporterManager(getReporterManager());
    }

    @Override
    protected ResourceBundle getActionModelResourceBundle() {
        return null;
    }

    @Override
    public <B extends NamedBean> Comparator<B> getNamedBeanComparator(Class<B> type) {
        return new NamedBeanComparator<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean provides(Class<?> type) {
        if (getDisabled()) {
            return false;
        }
        if (type.equals(jmri.TurnoutManager.class)) {
            return true;
        }
        if (type.equals(jmri.SensorManager.class)) {
            return true;
        }
        return false; // nothing, by default
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<?> T) {
        if (getDisabled()) {
            return null;
        }
        if (T.equals(jmri.TurnoutManager.class)) {
            return (T) getTurnoutManager();
        }
       if (T.equals(jmri.SensorManager.class)) {
            return (T) getSensorManager();
        }
        return null; // nothing, by default
    }

    protected MqttTurnoutManager turnoutManager;
    protected MqttSensorManager sensorManager;

    public MqttTurnoutManager getTurnoutManager() {
        if (getDisabled()) {
            return null;
        }
        if (turnoutManager == null) {
            turnoutManager = new MqttTurnoutManager(this);
        }
        return turnoutManager;
    }

    public MqttSensorManager getSensorManager() {
        if (getDisabled()) {
            return null;
        }
        if (sensorManager == null) {
            sensorManager = new MqttSensorManager(this);
        }
        return sensorManager;
    }

    void setMqttAdapter(MqttAdapter ma) {
        mqttAdapter = ma;
    }

    MqttAdapter getMqttAdapter() {
        return mqttAdapter;
    }
}
