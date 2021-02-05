package com.axiell.arena.rdf;

import com.axiell.arena.rdf.model.vocabulary.BIBFRAME;
import com.axiell.arena.rdf.model.vocabulary.CIDOC;
import com.axiell.arena.rdf.model.vocabulary.SCHEMA;
import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.DCTERMS;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.WriterConfig;
import org.eclipse.rdf4j.rio.helpers.BasicWriterSettings;
import org.eclipse.rdf4j.rio.helpers.JSONLDMode;
import org.eclipse.rdf4j.rio.helpers.JSONLDSettings;

import java.time.LocalDate;

public class CidocTest {

	public static void main(String[] args) {

		ValueFactory vf = SimpleValueFactory.getInstance();
		BNode height = vf.createBNode();
		BNode width = vf.createBNode();

		ModelBuilder builder = new ModelBuilder();
		builder
				.setNamespace(SCHEMA.PREFIX, SCHEMA.NAMESPACE)
				.setNamespace(DCTERMS.PREFIX, DCTERMS.NAMESPACE)
				.setNamespace(CIDOC.PREFIX, CIDOC.NAMESPACE)
				.subject("http://www.axiell.com/arena/1131-RHE")
				.add(RDF.TYPE, CIDOC.E22_Man_Made_Object)
				.add(RDFS.LABEL, "The Betrayal of Christ")
				.add(CIDOC.P43_has_dimension, height)
				.add(CIDOC.P43_has_dimension, width)
				.subject(height)
				.add(RDF.TYPE, CIDOC.E54_Dimension)
				.add(CIDOC.P90_has_value, 142.2)
				.add(CIDOC.P91_has_unit, "CMT")
				.subject(width)
				.add(RDF.TYPE, CIDOC.E54_Dimension)
				.add(CIDOC.P90_has_value, 87.9)
				.add(CIDOC.P91_has_unit, "CMT");
		Model model = builder.build();

		WriterConfig writerConfig = (new WriterConfig()).useDefaults();
		writerConfig.set(BasicWriterSettings.PRETTY_PRINT, true);
		writerConfig.set(JSONLDSettings.JSONLD_MODE, JSONLDMode.COMPACT);
		writerConfig.set(JSONLDSettings.OPTIMIZE, true);
		writerConfig.set(JSONLDSettings.HIERARCHICAL_VIEW, true);
		Rio.write(model, System.out, RDFFormat.RDFXML, writerConfig);
		// Rio.write(model, System.out, RDFFormat.TURTLE);
	}
}
