package cn.thisfree.autocode.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 报表工具类
 * @author xiaolong.huang
 *
 */
public class ReportUtils {
	private static Map<String,List<String>> map=new TreeMap<String,List<String>>();
	static{
		//柱状图
		List<String> barList=new ArrayList<String>();
		barList.add("bar");
		barList.add("bar1");
		barList.add("bar2");
		barList.add("bar3");
		barList.add("bar4");
		barList.add("bar5");
		barList.add("bar6");
		barList.add("bar7");
		barList.add("bar8");
		barList.add("bar9");
		map.put("bar",barList);
		
		List<String> chordList=new ArrayList<String>();
		chordList.add("chord");
		chordList.add("chord1");
		chordList.add("chord2");
		chordList.add("chord3");
		chordList.add("chord4");
		map.put("chord",chordList);
		
		List<String> eventRiverList=new ArrayList<String>();
		eventRiverList.add("eventRiver1");
		eventRiverList.add("eventRiver2");
		map.put("eventRiver",eventRiverList);
		
		List<String> forceList=new ArrayList<String>();
		forceList.add("force");
		forceList.add("force1");
		forceList.add("force2");
		forceList.add("force3");
		forceList.add("force4");
		map.put("force",forceList);
		
		List<String> funnelList=new ArrayList<String>();
		funnelList.add("funnel");
		funnelList.add("funnel1");
		funnelList.add("funnel2");
		funnelList.add("funnel3");
		funnelList.add("funnel4");
		map.put("funnel",funnelList);
		
		List<String> gaugeList=new ArrayList<String>();
		gaugeList.add("gauge");
		gaugeList.add("gauge1");
		gaugeList.add("gauge2");
		gaugeList.add("gauge3");
		gaugeList.add("gauge4");
		gaugeList.add("gauge5");
		map.put("gauge",gaugeList);
		
		List<String> heatmapList=new ArrayList<String>();
		heatmapList.add("heatmap");
		heatmapList.add("heatmap1");
		heatmapList.add("heatmap2");
		map.put("heatmap",heatmapList);
		
		List<String> kList=new ArrayList<String>();
		kList.add("k");
		kList.add("k1");
		map.put("k",kList);
		
		List<String> lineList=new ArrayList<String>();
		lineList.add("line");
		lineList.add("line1");
		lineList.add("line2");
		lineList.add("line3");
		lineList.add("line4");
		lineList.add("line5");
		lineList.add("line6");
		lineList.add("line7");
		lineList.add("line8");
		lineList.add("line9");
		lineList.add("line10");
		lineList.add("line11");
		map.put("line",lineList);
		
		List<String> mapList=new ArrayList<String>();
		mapList.add("map");
		mapList.add("map1");
		mapList.add("map2");
		mapList.add("map3");
		mapList.add("map4");
		mapList.add("map5");
		mapList.add("map6");
		mapList.add("map7");
		mapList.add("map8");
		mapList.add("map9");
		mapList.add("map10");
		mapList.add("map11");
		mapList.add("map12");
		mapList.add("map13");
		mapList.add("map14");
		mapList.add("map15");
		mapList.add("map16");
		mapList.add("map17");
		mapList.add("map18");
		mapList.add("map19");
		mapList.add("map20");
		mapList.add("map21");
		mapList.add("map22");
		map.put("map",mapList);
		
		List<String> pieList=new ArrayList<String>();
		pieList.add("pie");
		pieList.add("pie1");
		pieList.add("pie2");
		pieList.add("pie3");
		pieList.add("pie4");
		pieList.add("pie5");
		pieList.add("pie6");
		pieList.add("pie7");
		map.put("pie",pieList);
		
		List<String> radarList=new ArrayList<String>();
		radarList.add("radar");
		radarList.add("radar1");
		radarList.add("radar2");
		radarList.add("radar3");
		map.put("radar",radarList);
		
		List<String> scatterList=new ArrayList<String>();
		scatterList.add("scatter");
		scatterList.add("scatter1");
		scatterList.add("scatter2");
		scatterList.add("scatter3");
		scatterList.add("scatter4");
		scatterList.add("scatter5");
		scatterList.add("scatter6");
		map.put("scatter",scatterList);
		
		List<String> treeList=new ArrayList<String>();
		treeList.add("tree");
		treeList.add("tree1");
		treeList.add("tree2");
		treeList.add("tree3");
		treeList.add("tree4");
		treeList.add("tree5");
		map.put("tree",treeList);
		
		List<String> treemapList=new ArrayList<String>();
		treemapList.add("treemap");
		treemapList.add("treemap1");
		treemapList.add("treemap2");
		treemapList.add("treemap3");
		map.put("treemap",treemapList);
		
		List<String> vennList=new ArrayList<String>();
		vennList.add("venn");
		vennList.add("venn1");
		map.put("venn",vennList);
		
		List<String> wordCloudList=new ArrayList<String>();
		wordCloudList.add("wordCloud");
		map.put("wordCloud",wordCloudList);
	}
	
	/**
	 * 获取报表类型
	 * @return
	 */
	public static String[] getTypes(){
		return map.keySet().toArray(new String[]{});
	}
	
	/**
	 * 获取具体的类型名
	 * @param type
	 * @return
	 */
	public static String[] getNamesByType(String type){
		return map.get(type).toArray(new String[]{});
	}
}
