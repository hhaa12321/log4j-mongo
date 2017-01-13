/*
 * Copyright (C) 2009 Peter Monks (pmonks@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xyz.wang11.log4mongo;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.mongodb.DBObject;

/**
 * Abstract Log4J Appender class that stores log events in the BSON format. Concrete
 * implementation classes must implement append(DBObject) to store the BSON
 * representation of a LoggingEvent.
 *
 * @see <a href="http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/Appender.html">Log4J Appender Interface</a>
 * @see <a href="http://www.mongodb.org/">MongoDB</a>
 */
public abstract class BsonAppender extends AppenderSkeleton {
    private LoggingEventBsonifier bsonifier = new LoggingEventBsonifierImpl();
    
    /**
     * @see org.apache.log4j.Appender#requiresLayout()
     */
    public boolean requiresLayout() {
        return(false);
    }

    /**
     * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
     */
    @Override
    protected void append(final LoggingEvent loggingEvent) {
        DBObject bson = bsonifier.bsonify(loggingEvent);
        append(bson);
    }

    /**
     * Method implemented by a concrete class to store the BSON object.
     *  
     * @param bson The BSON representation of a Logging Event that will be stored
     */
    protected abstract void append(DBObject bson);

    /**
     * @return Object used to Bsonify LoggingEvent objects
     */
    public LoggingEventBsonifier getBsonifier() {
        return bsonifier;
    }

    /**
     * @param bsonifier Object used to Bsonify LoggingEvent objects
     */
    public void setBsonifier(LoggingEventBsonifier bsonifier) {
        this.bsonifier = bsonifier;
    }
    
}
