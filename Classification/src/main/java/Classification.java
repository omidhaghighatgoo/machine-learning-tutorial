import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;

public class Classification {


    public void toClassify() throws Exception {


        DataSource source = new DataSource("zoo.arff");
        Instances data= source.getDataSet();

        Remove remove = new Remove();
        remove.setOptions(new String[] {"-R" , "1"});
        remove.setInputFormat(data);
        Instances preparedData = Filter.useFilter(data , remove);

        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator(new InfoGainAttributeEval());
        attributeSelection.setSearch(new Ranker());
        attributeSelection.SelectAttributes(preparedData);

        int[] selectedAttribute = attributeSelection.selectedAttributes();
        System.out.println(Utils.arrayToString(selectedAttribute));

        J48 tree = new J48();
        tree.setOptions(new String[]{"-U"});
        tree.buildClassifier(preparedData);
        System.out.println(tree.toString());

        TreeVisualizer treeVisualizer = new TreeVisualizer(null , tree.graph(), new PlaceNode2());
        JFrame jFrame = new JFrame("Virtual Learning Youtube Channel");
        jFrame.setSize(1920,1080);
        jFrame.getContentPane().add(treeVisualizer);
        jFrame.setVisible(true);
        treeVisualizer.fitToScreen();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
