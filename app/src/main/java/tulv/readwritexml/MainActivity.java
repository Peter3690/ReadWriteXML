package tulv.readwritexml;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            SAXXML();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }


    public void readDOMXML() {
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();
            InputStream in = this.getAssets().open("books.xml");

            Document doc = builder.parse(in);
            Element root = doc.getDocumentElement(); //lấy tag Root ra
            NodeList list = root.getChildNodes();// lấy toàn bộ node con của Root
            String datashow = "";//biến để lưu thông tin
            for (int i = 0; i < list.getLength(); i++) // duyệt từ node đầu tiên cho tới node cuối cùng
            {
                Node node = list.item(i);// mỗi lần duyệt thì lấy ra 1 node
                if (node instanceof Element) // kiểm tra xem node đó có phải là Element hay không, vì ta dựa vào element để lấy dữ liệu bên trong
                {
                    Element employee = (Element) node;// lấy được tag Employee ra
                    String id = employee.getAttribute("id");//id là thuộc tính của tag Employee
                   /* String title=employee.getAttribute("title");//title là thuộc tính của tag employee*/
                    NodeList listChild = employee.getElementsByTagName("title");// lấy tag name bên trong của tag Employee
                    String name = listChild.item(0).getTextContent();//lấy nội dung của tag name
                    listChild = employee.getElementsByTagName("price");// lấy tag phone bên trong của tag Employee
                    String phone = listChild.item(0).getTextContent();// lấy nội dung của tag phone</span>
                    datashow += id + "-" + name + "-" + phone + "\n---------\n";//lưu vào biến lưu thông tin
                }
            }
            Toast.makeText(MainActivity.this, "" + datashow, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SAXXML() throws IOException, XmlPullParserException {
        XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
        XmlPullParser parser = fc.newPullParser();
        InputStream fIn = this.getAssets().open("books.xml");
        //duyet
        parser.setInput(fIn, "UTF-8");

        int eventType = -1;
        String nodeName;
        String datashow = "";
        while (eventType != XmlPullParser.END_DOCUMENT)//chưa kết thúc tài liệu
        {
            eventType = parser.next();// bắt đầu duyệt để
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG://là tag mở
                    nodeName = parser.getName();
                    if (nodeName.equals("book")) {// kiểm tra đúng tag mình muốn hay không
                        datashow += parser.getAttributeValue(0) + "-";//lấy giá trị của thuộc tính
                    } else if (nodeName.equals("title")) {
                    /*    datashow+=parser.nextText()+"-";//lấy nội dung tag element*/
                    } else if (nodeName.equals("price")) {
                        /*datashow+=parser.nextText()+"-";*/
                    }
                    break;
                case XmlPullParser.END_TAG://là tag đóng
                    nodeName = parser.getName();
                    if (nodeName.equals("book")) {
                        datashow += "\n----------------\n";
                    }
                    break;
            }
        }
        Toast.makeText(MainActivity.this, "" + datashow, Toast.LENGTH_SHORT).show();
    }

}
