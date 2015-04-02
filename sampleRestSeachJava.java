import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/*****************************************************************************************
 �@����Ȃ�Web�T�[�r�X�̃��X�g��������API�ňܓx�o�x���������s���p�[�X����v���O����
 �@���ӁF�����ł͈ܓx�ƌo�x�̒l�͌Œ�ł���Ă��܂��B
 �@�@�@�@�A�N�Z�X�L�[�̒l�ɂ̓��[�U�o�^�Ŏ擾�������̂����Ă��������B
*****************************************************************************************/

public class sampleJava {
    // �\�����s���v�f
    private static ArrayList<String> list = new ArrayList<String>();

    public static void main (String[] args) {
        //�@API�A�N�Z�X�L�[        
        String acckey = "input your accesskey";
        //  �ܓx
        String lat = "35.670082";
�@�@�@�@//  �o�x
        String lon = "139.763267";
�@�@�@�@//�@�͈́@1��300m 
        String range = "1";

        // URI�̑g�ݗ���
        String gnaviRestUri = "http://api.gnavi.co.jp/ver1/RestSearchAPI/?format=xml";
        String prmKeyid = "&keyid=" + acckey;
        String prmLat = "&latitude=" + lat;
        String prmLon = "&longitude=" + lon;
        String prmRange = "&range=" + range; 
        
        StringBuffer uri = new StringBuffer();
        uri.append(gnaviRestUri);
        uri.append(prmKeyid);
        uri.append(prmLat);
        uri.append(prmLon);
        uri.append(prmRange);
        
        // �\���v�f�̐ݒ�
        list.add("id");
        list.add("name");
        list.add("line");
        list.add("station");
        list.add("walk");

        // rest��NodeList�擾
        NodeList nodeList = getRestNodeList(uri.toString());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            viewNode(node);
        }
    }
   
    /**
     * rest�̃m�[�h�ꗗ�擾����
     *
     * @param String url
     * @return NodeList
     */ 
    private static NodeList getRestNodeList(String url) {
        NodeList nodeList = null;
        try {
            
            // �f�[�^�̎擾����
            URL restSearch = new URL(url);
            HttpURLConnection http = (HttpURLConnection)restSearch.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // Document�̐�������
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(http.getInputStream());

            // rest �̃m�[�h�ꗗ�̎擾
            nodeList = doc.getElementsByTagName("rest");

        } catch (Exception e) {
            // TODO:�G���[�����͍l�����Ă��܂���
        }

        return nodeList;
    }

    /**
     * �v�f�̏o�͏���
     *
     * @param Node node
     */
    private static void viewNode(Node node) {
        for(Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

            if (child.getNodeType() == Node.TEXT_NODE && list.contains(node.getNodeName())) {
                // �m�[�h���̏o��
                System.out.println(child.getNodeValue());
            } else {
                viewNode(child);
            }
        }
    }
}

