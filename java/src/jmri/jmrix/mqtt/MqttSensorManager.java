package jmri.jmrix.mqtt;

import javax.annotation.Nonnull;
import jmri.JmriException;
import jmri.Sensor;

/**
 * Implement sensor manager for MQTT systems
 * <p>
 * System names are "MSnnn", where M is the user configurable system prefix, nnn
 * is the sensor number without padding.
 *
 * @author Lionel Jeanson Copyright (c) 2017
 */
public class MqttSensorManager extends jmri.managers.AbstractSensorManager {

    /**
     *
     * @param ma the adapter for this manager
     * @param p  an ignored value
     * @deprecated since 4.17.3; use {@link #MqttSensorManager(jmri.jmrix.mqtt.MqttSystemConnectionMemo)} instead
     */
    @Deprecated
    public MqttSensorManager(@Nonnull MqttAdapter ma, @Nonnull String p) {
        this(ma.getSystemConnectionMemo());
    }

    public MqttSensorManager(@Nonnull MqttSystemConnectionMemo memo) {
        super(memo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    public MqttSystemConnectionMemo getMemo() {
        return (MqttSystemConnectionMemo) memo;
    }

    public void setTopicPrefix(@Nonnull String topicPrefix) {
        this.topicPrefix = topicPrefix;
    }
    @Nonnull
    public String topicPrefix = "track/sensor/"; // for constructing topic; public for script access

    /**
     * {@inheritDoc}
     * <p>
     * Accepts any string.
     */
    @Override
    public String createSystemName(@Nonnull String topicSuffix, @Nonnull String prefix) throws JmriException {
        return prefix + typeLetter() + topicSuffix;
    }

    @Override
    public Sensor createNewSensor(@Nonnull String systemName, String userName) {
        MqttSensor t;
        String suffix = systemName.substring(getSystemNamePrefix().length());
        String topic = topicPrefix + suffix;

        t = new MqttSensor(getMemo().getMqttAdapter(), systemName, topic);
        t.setUserName(userName);

        if (parser != null) {
            t.setParser(parser);
        }

        return t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEntryToolTip() {
        return "A string which will be appended to \"" + getMemo().getMqttAdapter().baseTopic + topicPrefix + "\"";
    }

    public void setParser(MqttContentParser<Sensor> parser) {
        this.parser = parser;
    }
    MqttContentParser<Sensor> parser = null;

    // private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MqttSensorManager.class);
}
