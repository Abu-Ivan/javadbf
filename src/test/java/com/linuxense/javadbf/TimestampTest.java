package com.linuxense.javadbf;

import org.junit.Test;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mauri on 20/08/2016.
 */
public class TimestampTest {
    public TimestampTest() {
        super();
    }

    @Test
    public void testTimestampRead () throws IOException {
    	DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream("src/test/resources/bdays.dbf"));

            int numberOfFields = reader.getFieldCount();
            Assert.assertEquals(3, numberOfFields);

            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                Assert.assertNotNull(field.getName());
            }

            Object[] rowObject;
            int countedRows = 0;
            while ((rowObject = reader.nextRecord()) != null) {
                Assert.assertEquals(numberOfFields, rowObject.length);
                Assert.assertTrue(rowObject[1] instanceof java.util.Date);

                Calendar calendar = new GregorianCalendar();
                calendar.set(1942,Calendar.NOVEMBER,27,10,15,0);
                calendar.clear(Calendar.MILLISECOND);

                Assert.assertTrue(rowObject[0].toString().trim().equals("jimi"));
                Assert.assertEquals(calendar.getTime(),(Date)rowObject[1]);
                Assert.assertTrue(rowObject[2].toString().trim().equals("hendrix"));

                countedRows++;
            }
            Assert.assertEquals(1, countedRows);
        }
        finally {
        	DBFUtils.close(reader);
        }
    }
}
