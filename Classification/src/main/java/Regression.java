import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Regression {

    public void doRegression() {

        try {
            CSVLoader loader = new CSVLoader();
            loader.setFieldSeparator(",");
            loader.setSource(new File("buildingdata.csv"));
            Instances instances = loader.getDataSet();
            System.out.println(instances.toString());


            Remove remove = new Remove();
            remove.setOptions(new String[]{"-R", "10"}); //  cooling_load is 10th column
            remove.setInputFormat(instances);
            Instances preparedData = Filter.useFilter(instances, remove);
            System.out.println(instances.numAttributes());

            preparedData.setClassIndex(8); //  8 is the index of heating_load which we want to work on
            System.out.println(preparedData.numAttributes() - 1);
            LinearRegression model = new LinearRegression();
            model.buildClassifier(preparedData);
            System.out.println(model);

            Evaluation evaluation = new Evaluation(preparedData);
            evaluation.crossValidateModel(model, preparedData , 10 , new Random(5) , new Object[]{});
            System.out.println(evaluation.toSummaryString());




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
