package com.axiell.arena.rdf;

import com.axiell.arena.rdf.model.vocabulary.SCHEMA;
import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.RioSetting;
import org.eclipse.rdf4j.rio.WriterConfig;
import org.eclipse.rdf4j.rio.helpers.BasicWriterSettings;
import org.eclipse.rdf4j.rio.helpers.JSONLDMode;
import org.eclipse.rdf4j.rio.helpers.JSONLDSettings;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Date;

public class Rdf3Test {

	public static void main(String[] args) throws URISyntaxException {

		ValueFactory vf = SimpleValueFactory.getInstance();
		BNode author = vf.createBNode();

		ModelBuilder builder = new ModelBuilder();
		builder
				.setNamespace(SCHEMA.PREFIX, SCHEMA.NAMESPACE)
				.subject("http://www.axiell.com/arena/1234")
					.add(RDF.TYPE, SCHEMA.Book)
					.add(SCHEMA.name, "The Catcher in the Rye")
					.add(SCHEMA.isbn, "031676947")
				    .add(SCHEMA.author, author)
				.subject(author)
				    .add(RDF.TYPE, SCHEMA.Person)
				    .add(SCHEMA.name, "J.D. Salinger")
				    .add(SCHEMA.birthDate, LocalDate.of(1919,1,1))
				    .add(SCHEMA.deathDate, LocalDate.of(2010,1,27));

		Model model = builder.build();

		WriterConfig writerConfig = (new WriterConfig()).useDefaults();
		writerConfig.set(BasicWriterSettings.PRETTY_PRINT, true);
		writerConfig.set(JSONLDSettings.JSONLD_MODE, JSONLDMode.COMPACT);
		writerConfig.set(JSONLDSettings.OPTIMIZE, true);
		writerConfig.set(JSONLDSettings.HIERARCHICAL_VIEW, true);
		Rio.write(model, System.out, RDFFormat.JSONLD, writerConfig);
	}
}
