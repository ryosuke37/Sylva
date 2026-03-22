package io.github.ryosuke37.sylva.repository.generator;

import java.io.Serializable;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

public class UuidV7Generator implements IdentifierGenerator {

    private static final TimeBasedEpochGenerator GENERATOR;

    static {
        GENERATOR = Generators.timeBasedEpochGenerator();
    }

    @Override
    public String generate(SharedSessionContractImplementor session, Object obj) {
        return GENERATOR.generate().toString();
    }
}