package cn.omvn.acwing.tool;

import cn.omvn.acwing.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class ProblemContentExtractor {


    public static Problem extractContent(String html) {
        Document doc = Jsoup.parse(html);
        Problem problem = new Problem();
        StringBuilder descriptionBuilder = new StringBuilder();

        // 提取标题和题目编号
        Element titleElement = doc.select("div.problem-content-title").first();
        if (titleElement != null) {
            problem.setTitle(titleElement.text());
        } else {
            problem.setTitle("题目编号和名称未找到");
        }

        // 提取题目描述、表格和图片
        Elements descriptionElements = doc.select("div.section-martor");
        if (!descriptionElements.isEmpty()) {
            for (Element section : descriptionElements) {
                // 提取描述文本
                Elements paragraphs = section.select("p:not(:has(table)), img");
                for (Element element : paragraphs) {
                    if (element.tagName().equals("p")) {
                        descriptionBuilder.append(element.text()).append("\n\n");
                    } else if (element.tagName().equals("img")) {
                        // 提取图片
                        String imgUrl = element.absUrl("src");
                        // 将图片转换为Markdown格式
                        descriptionBuilder.append("![](").append(imgUrl).append(")").append("\n\n");
                    }
                }

                // 提取表格内容
                Elements tables = section.select("table");
                for (Element table : tables) {
                    Elements rows = table.select("tr");
                    for (int i = 0; i < rows.size(); i++) {
                        Elements cols = rows.get(i).select("th, td");
                        for (int j = 0; j < cols.size(); j++) {
                            descriptionBuilder.append("| ").append(cols.get(j).text()).append(" ");
                        }
                        descriptionBuilder.append("|\n"); // 表格行结束

                        // 添加 Markdown 表格头分隔符
                        if (i == 0) {
                            for (int j = 0; j < cols.size(); j++) {
                                descriptionBuilder.append("|---");
                            }
                            descriptionBuilder.append("|\n");
                        }
                    }
                    descriptionBuilder.append("\n"); // 表格结束后换行
                }
                Elements inputFormat = section.select("h4");
                for (Element inputFormatElement : inputFormat) {

                }
            }
            problem.setContent(descriptionBuilder.toString());
        } else {

            problem.setContent("题目描述未找到");
        }

        return problem;
    }

//    public static Problem extractContent(String html) {
//        Document doc = Jsoup.parse(html);
//        Problem problem = new Problem();
//        StringBuilder descriptionBuilder = new StringBuilder();
//        StringBuilder inputFormatBuilder = new StringBuilder();
//        StringBuilder outputFormatBuilder = new StringBuilder();
//        StringBuilder dataRangeBuilder = new StringBuilder();
//
//        // 提取标题和题目编号
//        Element titleElement = doc.select("div.problem-content-title").first();
//        if (titleElement != null) {
//            problem.setTitle(titleElement.text());
//        } else {
//            problem.setTitle("题目编号和名称未找到");
//        }
//
//        // 提取题目描述、表格和图片
//        Elements descriptionElements = doc.select("div.section-martor");
//        if (!descriptionElements.isEmpty()) {
//            StringBuilder currentBuilder = descriptionBuilder; // 当前正在使用的StringBuilder
//            for (Element section : descriptionElements) {
//                Elements children = section.select("p"); // 获取所有子元素
//
//                for (Element child : children) {
//
//                    // 检查是否是h4标签
//                    if ("h4".equals(child.tagName())) {
//                        String h4Text = child.text();
//                        if ("输入格式".equals(h4Text)) {
//                            currentBuilder = inputFormatBuilder;
//                        } else if ("输出格式".equals(h4Text)) {
//                            currentBuilder = outputFormatBuilder;
//                        } else if ("数据范围".equals(h4Text)) {
//                            currentBuilder = dataRangeBuilder;
//                        } else {
//                            currentBuilder = descriptionBuilder;
//                        }
//                    }
//                    // 如果不是h4, table, img标签，将其文本内容添加到当前构建器
//                    if (!"h4".equals(child.tagName()) && !"table".equals(child.tagName()) && !"img".equals(child.tagName())) {
//                        currentBuilder.append(child.text()).append("\n\n");
//                    } else if ("img".equals(child.tagName())) {
//                        System.out.println("img");
//                        // 提取图片
//                        String imgUrl = child.absUrl("src");
//                        // 将图片转换为Markdown格式
//                        currentBuilder.append("![](").append(imgUrl).append(")").append("\n\n");
//                    }
////                    else if ("table".equals(child.tagName())) {
////                        // 提取表格内容...
////                        // ...这里省略表格内容提取的代码...
////                        Elements tables = section.select("table");
////                        for (Element table : tables) {
////                            Elements rows = table.select("tr");
////                            for (int i = 0; i < rows.size(); i++) {
////                                Elements cols = rows.get(i).select("th, td");
////                                for (int j = 0; j < cols.size(); j++) {
////                                    descriptionBuilder.append("| ").append(cols.get(j).text()).append(" ");
////                                }
////                                descriptionBuilder.append("|\n"); // 表格行结束
////
////                                // 添加 Markdown 表格头分隔符
////                                if (i == 0) {
////                                    for (int j = 0; j < cols.size(); j++) {
////                                        descriptionBuilder.append("|---");
////                                    }
////                                    descriptionBuilder.append("|\n");
////                                }
////                            }
////                            descriptionBuilder.append("\n"); // 表格结束后换行
////                        }
////                    }
//                }
//            }
//            problem.setContent(descriptionBuilder.toString());
//            problem.setInputFormat(inputFormatBuilder.toString());
//            problem.setOutputFormat(outputFormatBuilder.toString());
//            problem.setExplain(dataRangeBuilder.toString());
//        } else {
//            problem.setContent("题目描述未找到");
//        }
//
//        return problem;
//    }

}





