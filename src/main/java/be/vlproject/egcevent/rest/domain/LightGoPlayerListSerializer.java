package be.vlproject.egcevent.rest.domain;

import be.vlproject.egcevent.tournament.domain.GoPlayer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;
import java.util.List;

public class LightGoPlayerListSerializer extends JsonSerializer<List<GoPlayer>> {

    @Override
    public void serializeWithType(List<GoPlayer> value, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(value, gen, serializers);
    }

    @Override
    public void serialize(List<GoPlayer> objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (GoPlayer goPlayer : objects) {
            jsonGenerator.writeObject(goPlayer);
//            jsonGenerator.writeStartObject();
//            /*
//                lastName;
//                firstName;
//                level;
//                email;
//             */
//            jsonGenerator.writeStringField("lastName", goPlayer.getLastName());
//            jsonGenerator.writeStringField("firstName", goPlayer.getFirstName());
//            jsonGenerator.writeStringField("level", goPlayer.getLevel());
//            jsonGenerator.writeStringField("email", goPlayer.getEmail());
//            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

    }
}
