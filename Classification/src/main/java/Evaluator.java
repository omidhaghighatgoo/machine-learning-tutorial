import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.util.Random;

public class Evaluator {


    public void toEvaluate() {


        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource("zoo.arff");
            Instances data = source.getDataSet();

            Remove remove = new Remove();
            remove.setOptions(new String[]{"-R", "1"});
            remove.setInputFormat(data);
            Instances preparedData = Filter.useFilter(data, remove);

            AttributeSelection attributeSelection = new AttributeSelection();
            attributeSelection.setEvaluator(new InfoGainAttributeEval());
            attributeSelection.setSearch(new Ranker());
            attributeSelection.SelectAttributes(preparedData);

            Classifier cl = new J48();
            Evaluation evaluation = new Evaluation(preparedData);
            evaluation.crossValidateModel(cl, preparedData, 10 , new Random(3) , new Object[]{});
            System.out.println(evaluation.toSummaryString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
