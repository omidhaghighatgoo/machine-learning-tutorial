import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;


public class Classification {




    public void toClassify() throws Exception {
        DataSource source = new DataSource("zoo.arff");
        Instances data = source.getDataSet();
        System.out.println(data.numInstances() + " instances loaded.");
        System.out.println(data.toString());


        System.out.println("=============================================================");

        Remove remove = new Remove();
        remove.setOptions(new String[]{"-R" , "1"});
        remove.setInputFormat(data);
        Instances preparedData = Filter.useFilter(data,remove);
        System.out.println(preparedData.toString());


        System.out.println("=============================================================");


        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator( new InfoGainAttributeEval());
        attributeSelection.setSearch( new Ranker());
        attributeSelection.SelectAttributes(preparedData);

        int[] selectedAttributes = attributeSelection.selectedAttributes();

        System.out.println(selectedAttributes.length);
        System.out.println(Utils.arrayToString(selectedAttributes));


    }
}
