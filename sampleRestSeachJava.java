import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/*****************************************************************************************
 　ぐるなびWebサービスのレストラン検索APIで緯度経度検索を実行しパースするプログラム
 　注意：ここでは緯度と経度の値は固定でいれています。
 　　　　アクセスキーの値にはユーザ登録で取得したものを入れてください。
*****************************************************************************************/

public class sampleJava {
    // 表示を行う要素
    private static ArrayList<String> list = new ArrayList<String>();

    public static void main (String[] args) {
        //　APIアクセスキー        
        String acckey = "input your accesskey";
        //  緯度
        String lat = "35.670082";
　　　　//  経度
        String lon = "139.763267";
　　　　//　範囲　1は300m 
        String range = "1";

        // URIの組み立て
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
        
        // 表示要素の設定
        list.add("id");
        list.add("name");
        list.add("line");
        list.add("station");
        list.add("walk");

        // restのNodeList取得
        NodeList nodeList = getRestNodeList(uri.toString());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            viewNode(node);
        }
    }
   
    /**
     * restのノード一覧取得処理
     *
     * @param String url
     * @return NodeList
     */ 
    private static NodeList getRestNodeList(String url) {
        NodeList nodeList = null;
        try {
            
            // データの取得処理
            URL restSearch = new URL(url);
            HttpURLConnection http = (HttpURLConnection)restSearch.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            // Documentの生成処理
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(http.getInputStream());

            // rest のノード一覧の取得
            nodeList = doc.getElementsByTagName("rest");

        } catch (Exception e) {
            // TODO:エラー処理は考慮していません
        }

        return nodeList;
    }

    /**
     * 要素の出力処理
     *
     * @param Node node
     */
    private static void viewNode(Node node) {
        for(Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {

            if (child.getNodeType() == Node.TEXT_NODE && list.contains(node.getNodeName())) {
                // ノード情報の出力
                System.out.println(child.getNodeValue());
            } else {
                viewNode(child);
            }
        }
    }
}

