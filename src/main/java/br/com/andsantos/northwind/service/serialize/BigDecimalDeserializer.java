package br.com.andsantos.northwind.service.serialize;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class BigDecimalDeserializer extends StdDeserializer<BigDecimal> {
    private static final long serialVersionUID = -7689419551477559989L;

    protected BigDecimalDeserializer() {
        super(BigDecimal.class);
    }

    @Override
    public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        String pattern = "#,##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        if (jp.getText() != null) {
            try {
                return (BigDecimal) decimalFormat.parse(jp.getText());
            } catch (ParseException e) {
                throw new IOException(e);
            }
        } else {
            return null;
        }
    }
}
