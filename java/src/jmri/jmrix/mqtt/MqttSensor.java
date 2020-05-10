package jmri.jmrix.mqtt;

import javax.annotation.Nonnull;
import jmri.Sensor;
import jmri.implementation.AbstractSensor;

/**
 * Implementation of the Sensor interface for MQTT layouts.
 *
 * @author Lionel Jeanson Copyright (c) 2017
 */
public class MqttSensor extends AbstractSensor implements MqttEventListener {

    private final MqttAdapter mqttAdapter;
    private final String topic;

    /**
     * Requires, but does not check, that the system name and topic be consistent
     */
    MqttSensor(MqttAdapter ma, String systemName, String topic) {
        super(systemName);
        this.topic = topic;
        mqttAdapter = ma;
        mqttAdapter.subscribe(this.topic, this);
    }

    public void setParser(MqttContentParser<Sensor> parser) {
        this.parser = parser;
    }
    
    MqttContentParser<Sensor> parser = new MqttContentParser<Sensor>() {
        private final static String activeText = "ACTIVE";
        private final static String inactiveText = "INACTIVE";
        @Override
        public void beanFromPayload(@Nonnull Sensor bean, @Nonnull String payload, @Nonnull String topic) {
            switch (payload) {
                case activeText:                
                    setOwnState(ACTIVE);
                    break;
                case inactiveText:
                    setOwnState(INACTIVE);
                    break;
                default:
                    log.warn("Unknown state : {}", payload);
                    break;
            }
        }
        
        @Override
        public @Nonnull String payloadFromBean(@Nonnull Sensor bean, int newState){
            // sort out states
            if ((newState & Sensor.ACTIVE) != 0 ^ getInverted()) {
                // first look for the double case, which we can't handle
                if ((newState & Sensor.INACTIVE ) != 0 ^ getInverted()) {
                    // this is the disaster case!
                    log.error("Cannot command both ACTIVE and INACTIVE: {}", newState);
                    throw new IllegalArgumentException("Cannot command both ACTIVE and INACTIVE: "+newState);
                } else {
                    // send a ACTIVE command
                    return activeText;
                }
            } else {
                // send a INACTIVE command
                return inactiveText;
            }
        }
    };
    

    // Sensors do support inversion
    @Override
    public boolean canInvert() {
        return true;
    }
    
    @Override
    public void requestUpdateFromLayout(){
            
    }
    // Handle a request to change state by sending a formatted DCC packet
    /*@Override
    protected void forwardCommandChangeToLayout(int s) {
        // sort out states
        String payload = parser.payloadFromBean(this, s);

        // send appropriate command
        sendMessage(payload);
    }*/

   /* @Override
    protected void sensorPushbuttonLockout(boolean _pushButtonLockout) {
        log.debug("Send command to {} Pushbutton BT{} not yet coded", (_pushButtonLockout ? "Lock" : "Unlock"), topic);
    }
*/
    private void sendMessage(String c) {
        mqttAdapter.publish(topic, c.getBytes());
    }

    @Override
    public void notifyMqttMessage(String receivedTopic, String message) {
        if (!receivedTopic.endsWith(topic)) {
            log.error("Got a message whose topic ({}) wasn't for me ({})", receivedTopic, topic);
            return;
        }
        
        parser.beanFromPayload(this, message, receivedTopic);
    }

    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MqttSensor.class);

}
