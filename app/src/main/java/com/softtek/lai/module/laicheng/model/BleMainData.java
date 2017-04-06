package com.softtek.lai.module.laicheng.model;

import java.util.List;

/**
 * Created by jia.lu on 2017/4/5.
 */

public class BleMainData {

    /**
     * bodytype : 3
     * bodytypedescription : 肥胖型:<br/>你的BMI和体脂率均偏高，属于肥胖型身材，这会增加日后患糖尿病、高血压等代谢性疾病的风险。请减少能量摄入，注意均衡营养，多做跑步、快走等有氧运动。
     * weight_con : {"caption":"超重","color":"ff6666"}
     * weight_item : {"value":67.6,"unit":"kg","color":"ff6666","arrowcolor":"db5151","indexdescription":"<font color=\"#ff6666\">你的体重超标啦！<\/font><br />体重是反映和衡量一个人健康状况的重要指标之一，偏瘦或超重都不利于人体健康，也会影响一个人外形的美感。<br />请同时关注你的体脂率。如果体脂率和体重两个指标都高于理想值，说明你有肥胖的倾向；肥胖是各种慢性疾病酝酿的温床！请注意控制热量的摄入，采纳合理的营养搭配，每天运动至少30分钟，循序渐进，让营养顾问帮你制定一个科学的减重计划吧！请记住，减重不等于减肥，减脂才等于减肥哦！","range":[{"value":35,"color":"66b3ff"},{"value":52,"color":"93c952"},{"value":57.6,"color":"ff6666"},{"value":125}]}
     * bmi : 24.8
     * bmi_con : {"caption":"超重","color":"ff6666"}
     * bmi_item : {"value":24.8,"unit":"kg/m2","color":"ee8a17","arrowcolor":"bd6b0c","indexdescription":"<font color=\"#ff6666\">你的身材有点超重哦！<\/font><br />BMI是英文Body Mass Index的缩写，指身体质量指数，是用体重（kg）数除以身高（m）的平方得出的数值。<br />BMI是国际通用的衡量人体胖瘦程度及是否健康的一个标准；当我们需要分析一个人的体重是否标准时，采用BMI指数比单纯分析体重更可靠。<br />请同时关注你的体脂率。如果BMI超重且体脂率高于理想值，你就有肥胖的倾向哦！肥胖是各种慢性疾病酝酿的温床！请一定要注意控制热量的摄入，合理搭配膳食结构，每天运动至少30分钟，循序渐进，让营养顾问帮你制定一个科学的减重计划，健康好身材等着你！","range":[{"value":13,"color":"66b3ff"},{"value":18.5,"color":"93c952"},{"value":23,"color":"ee8a17"},{"value":25,"color":"ff6666"},{"value":40}]}
     * bodyfat : 19.7
     * bodyfat_item : {"value":19.7,"unit":"kg","color":"93c952","arrowcolor":"7ab138","indexdescription":"体脂肪指身体脂肪的总重量。<br />人体脂肪具有保温、储存和提供能量、保护身体组织等功能。因此，体脂肪应当保持在正常范围内，偏低或偏高都不好。偏低会引起身体功能失调，偏高会引起肥胖，肥胖并非指单纯性的体重过高，而是指异常或过量的体脂肪积累，这会增加多种代谢性疾病的患病风险。<br />请参照体脂率解读你的体脂肪状况。","range":[{"value":0,"color":"93c952"},{"value":60}]}
     * bodyfatrate : 29.1
     * bodyfatrate_con : {"caption":"肥胖","color":"ff6666"}
     * bodyfatrate_item : {"value":29.1,"unit":"%","color":"ee8a17","arrowcolor":"bd6b0c","indexdescription":"<font color=\"#ff6666\">您的体脂率超标啦！<\/font><br />体脂率指身体脂肪占身体总体重的百分比，是用来反映身体相对脂肪含量的指标。<br />体脂率低于或高于理想值，代表一个人的体脂肪情况出现异常，这与多种代谢性疾病的发病率有明显关系；体脂率比单纯以体重或BMI作为标准更能反映一个人的肥胖情况，也更适合判断一个人的减肥效果和减肥的方向是否正确；减重不等于减肥，减脂才等于减肥。<br />你的体脂率处于肥胖的范围了。肥胖会大幅增加高血压、II型糖尿病等疾病的患病风险。请立即控制能量摄入，减少高油高糖的食物的摄入，在营养顾问的指导下进行适当的运动，健康身材就在不远处！","range":[{"value":6,"color":"66b3ff"},{"value":9.1,"color":"7cc6b3"},{"value":19,"color":"93c952"},{"value":22.5,"color":"f9d133"},{"value":25.5,"color":"ee8a17"},{"value":29.6,"color":"ff6666"},{"value":46}]}
     * fatfreemass : 47.9
     * fatfreemass_con : {"caption":"理想","color":"93c952"}
     * fatfreemass_item : {"value":47.9,"unit":"kg","color":"93c952","arrowcolor":"7ab138","indexdescription":"<font color=\"#93c952\">你的去脂体重很理想，身材一定非常健美！<\/font><br />去脂体重指身体体重扣去体脂肪之后的重量，包括身体水分、蛋白质和无机盐三部分。<br />去脂体重中肌肉和骨骼占很大比重，这是影响身体活动能力的重要因素，测量去脂体重对促进能量转换和耗氧、调节水盐代谢等具有重要意义；通常在运动训练中，运动员保持较高的去脂体重，这对提高有氧耐力和运动能力很有好处；另外，去脂体重与人体的新陈代谢和生长发育密切相关。我们推荐每天每kg去脂体重摄入2g蛋白质，其中1/3到2/3的蛋白质通过膳食补充剂补充。<br />你的去脂体重很理想，这说明你的身体强壮、体质好，一般运动员或平时坚持参加体育锻炼的人，才可能像你一样肌肉强健；尽管你的体重可能不轻，但却看起来健康有力棒棒哒。请继续保持结构合理并且多样化的饮食，继续保持80%营养+20%运动的积极、健康生活方式！健美的好身体，让你充满魅力！","range":[{"value":25,"color":"66b3ff"},{"value":38.7,"color":"7cc6b3"},{"value":47.4,"color":"93c952"},{"value":75}]}
     * watercontent : 35.2
     * watercontent_item : {"value":35.2,"unit":"kg","color":"93c952","arrowcolor":"7ab138","indexdescription":"身体水分是人体细胞内水分与细胞外水分的总和。<br />充足的身体水分有助于增加身体代谢循环的速度，增加人体内废物和毒素的排出速度。水分过低或过高都不利于身体的代谢过程，影响身体健康，每天8杯水是维持人体水分平衡的基础。身体水分和肌肉量有着极其密切的关系，这项指标能够反应减重的方式是否正确，如果身体水分下降，不但有损健康，更会令体脂率上升。<br />请参照身体水分率解读你的身体水分状况。","range":[{"value":20,"color":"93c952"},{"value":65}]}
     * watercontentrate : 52.1
     * watercontentrate_con : {"caption":"达标","color":"93c952"}
     * watercontentrate_item : {"value":52.1,"unit":"%","color":"93c952","arrowcolor":"7ab138","indexdescription":"<font color=\"#93c952\">你的身体水分率已达标，棒棒哒！<\/font><br />身体水分率是身体水分占身体体重的比值，是反映人体水分相对含量的指标。<br />身体水分不仅构成了身体的主要成分，而且还有许多生理功能，是机体物质代谢必不可少的载体；人体细胞必须从组织间液摄取营养，而营养物质溶于水才能被充分吸收，物质代谢的中间产物和最终产物也必须通过组织间液运送和排除。因此，人体拥有适宜的身体水分率可以维持人体各种代谢活动的正常进行。<br />你的身体水分已达标，你一定有很好的喝水习惯，这对机体的代谢循环很有帮助。请你继续保持结构合理并且多样化的饮食习惯，继续保持80%营养+20%运动的积极、健康生活方式！继续做一名喝水达人吧！","range":[{"value":40,"color":"66b3ff"},{"value":51,"color":"93c952"},{"value":65}]}
     * visceralfatindex : 8
     * visceralfatindex_con : {"caption":"一般","color":"93c952"}
     * visceralfatindex_item : {"value":7.7,"color":"f9d133","arrowcolor":"e2b607","indexdescription":"<font color=\"#93c952\">您的内脏脂肪指数状况一般。<\/font><br />内脏脂肪指数是反映人体脏器（肝脏、胃、肠等器官）周围脂肪含量的指数。<br />由于脂蛋白酶在内脏较皮下更具活性，所以内脏脂肪较易脂解为脂肪微粒并通过血液运送到肝脏；大量的脂肪微粒涌入肝脏，是脂肪肝、高血压、高血脂及高胰岛素形成的主要原因，也容易引起心血管疾病，同时使体内积聚大量的水分。内脏脂肪的增加意味着肥胖和身体老化，特别是会增加各类慢性疾病的发病率，可以用来反映向心性肥胖的程度。较低的内脏脂肪指数可以大幅减少糖尿病、脂肪肝等疾病的发病率。<br />您现在的内脏脂肪指数正常范围内较高的水平，这表明您正处于向心性肥胖的边缘。向心性肥胖会增加脂肪肝、II型糖尿病等的患病风险。建议您注意日常的饮食，控制能量摄入，多进行跑步、骑车等有氧运动。关爱自己，保持健康。","range":[{"value":0,"color":"93c952"},{"value":4,"color":"f9d133"},{"value":10,"color":"ee8a17"},{"value":15,"color":"ff6666"},{"value":20}]}
     * bonemass : 2.6
     * bonemass_con : {"caption":"偏低","color":"ff6666"}
     * bonemass_item : {"value":2.6,"unit":"kg","color":"66b3ff","arrowcolor":"4996e1","indexdescription":"<font color=\"#93c952\">你的骨量偏低！<\/font><br />骨量是人体骨骼中矿物质的含量，主要由钙、磷等无机盐组成。<br />人体骨骼的强度与骨量的多少密切相关。不同年龄时期骨钙的含量是不同的，因此，峰值骨量就如同人体内的\u201c骨银行\u201d，年轻时峰值骨量越高，相当于在银行中的储蓄越多，可供人们日后消耗的骨量就越多，峰值骨量的多少主要是由遗传因素、营养状况和生活习惯等决定。人体骨量越高就越不容易发生关节炎、骨折和骨质疏松等疾病。<br />骨量偏低容易增加日后患骨质疏松的风险哦！增加骨量，需要补充钙，日常食物中含钙较多的有：牛奶、鸡蛋、豆制品、海带、紫菜、虾皮、海鱼等，特别是牛奶；食用含钙丰富的食物时，应避免过多食用含磷酸盐、草酸丰富的食物，以免影响钙的吸收；建议你多参加户外运动增加体内维生素D的合成，促进钙吸收，同时还需要注意补充胶原蛋白。胶原蛋白是是人体骨骼尤其是软骨组织的重要组成成分。关注好营养，拥抱大自然！","range":[{"value":1.5,"color":"66b3ff"},{"value":2.8,"color":"7cc6b3"},{"value":3.5,"color":"93c952"},{"value":5}]}
     * musclemass : 45.4
     * musclemass_con : {"caption":"理想","color":"93c952"}
     * musclemass_item : {"value":45.4,"unit":"kg","color":"93c952","arrowcolor":"7ab138","indexdescription":"<font color=\"#93c952\">你的肌肉量状况很理想，你的身材一定非常健美<\/font><br />肌肉量指人体肌肉的重量，肌肉是人体消耗能量最多的组分。<br />肌肉量与一个人的体质、年龄和运动量等多种因素有关。保证充足的蛋白质摄入，加上合理的运动，可以增加一个人的肌肉量。增加肌肉量不仅可以帮助增加人体能量消耗、提高身体机能，还可以使体型更美观，显得更年轻。<br />你现在的身材很健美，充满力量，可以媲美专业运动员了，让人羡慕哦！不过，随着年龄的增长，当一个人年老时，肌肉的力量会衰退，所以，保持运动习惯特别是肌肉训练的习惯应该是人生中应该长期坚持的事情。请继续保持结构合理并且多样化的饮食习惯，继续保持80%营养+20%运动的积极、健康生活方式！健美好身材，成就逆生长！","range":[{"value":25,"color":"66b3ff"},{"value":36.5,"color":"7cc6b3"},{"value":44.8,"color":"93c952"},{"value":75}]}
     * basalmetabolicrate : 1406
     * basalmetabolicrate_con : {"caption":"代谢偏慢","color":"ff6666"}
     * basalmetabolicrate_item : {"value":1405.5,"unit":"kcal/day","color":"7cc6b3","arrowcolor":"5fb59f","indexdescription":"<font color=\"#ff6666\">你的基础代谢偏慢！<\/font><br />基础代谢指人体每天维持基础的生命活动（包括心跳、呼吸和体温等）所需要的能量。基础代谢受肌肉活动、环境温度、食物摄入或精神紧张等因素影响。<br />你现在看见的基础代谢不是根据你的体重或BMI推算出来，而是根据你的体成分测量出来的，所以更准确。基础代谢越高，意味着不运动的状态下身体自身消耗的能量越高。你的基础代谢与你的肌肉量有密切关系。合理饮食，增加运动，有利于提高自身的基础代谢水平，也就更不容易长胖啦。<br />你的基础代谢略微有点低，如果增加更多的肌肉，你的基础代谢会提高至正常水平，这意味着你更不容易发胖！请继续保持营养结构合理、品类多样化的饮食习惯，保持并适度增加运动量！","range":[{"value":985.7,"color":"66b3ff","valuetip":"70%"},{"value":1126.5,"color":"5dc3e1","valuetip":"80%"},{"value":1267.4,"color":"7cc6b3","valuetip":"90%"},{"value":1408.2,"color":"93c952","valuetip":"100%"},{"value":1549,"valuetip":"110%"}]}
     * physicalage : 29
     * physicalage_con : {"color":"ff6666"}
     * physicalage_item : {"value":29.3,"unit":"","color":"ff6666","arrowcolor":"db5151","indexdescription":"身体年龄是当前身体状况所对应的生理年龄，判断标准是与实际年龄进行比较。<br />身体年龄越小意味着身体情况越健康，身体机能更年轻。身体年龄越大意味着身体情况越不健康，身体机能偏老化。<br />健康的身体，拥有小于实际年龄10-20岁的身体年龄。请养成结构合理的多样化饮食习惯，继续保持80%营养+20%运动的积极、健康生活方式，努力朝着更年轻的方向迈进吧！","range":[{"value":0,"color":"93c952"},{"value":19,"color":"ff6666"},{"value":59}]}
     */

    private int bodytype;
    private String bodytypedescription;
    private WeightConBean weight_con;
    private WeightItemBean weight_item;
    private double bmi;
    private BmiConBean bmi_con;
    private BmiItemBean bmi_item;
    private double bodyfat;
    private BodyfatItemBean bodyfat_item;
    private double bodyfatrate;
    private BodyfatrateConBean bodyfatrate_con;
    private BodyfatrateItemBean bodyfatrate_item;
    private double fatfreemass;
    private FatfreemassConBean fatfreemass_con;
    private FatfreemassItemBean fatfreemass_item;
    private double watercontent;
    private WatercontentItemBean watercontent_item;
    private double watercontentrate;
    private WatercontentrateConBean watercontentrate_con;
    private WatercontentrateItemBean watercontentrate_item;
    private int visceralfatindex;
    private VisceralfatindexConBean visceralfatindex_con;
    private VisceralfatindexItemBean visceralfatindex_item;
    private double bonemass;
    private BonemassConBean bonemass_con;
    private BonemassItemBean bonemass_item;
    private double musclemass;
    private MusclemassConBean musclemass_con;
    private MusclemassItemBean musclemass_item;
    private int basalmetabolicrate;
    private BasalmetabolicrateConBean basalmetabolicrate_con;
    private BasalmetabolicrateItemBean basalmetabolicrate_item;
    private int physicalage;
    private PhysicalageConBean physicalage_con;
    private PhysicalageItemBean physicalage_item;

    public int getBodytype() {
        return bodytype;
    }

    public void setBodytype(int bodytype) {
        this.bodytype = bodytype;
    }

    public String getBodytypedescription() {
        return bodytypedescription;
    }

    public void setBodytypedescription(String bodytypedescription) {
        this.bodytypedescription = bodytypedescription;
    }

    public WeightConBean getWeight_con() {
        return weight_con;
    }

    public void setWeight_con(WeightConBean weight_con) {
        this.weight_con = weight_con;
    }

    public WeightItemBean getWeight_item() {
        return weight_item;
    }

    public void setWeight_item(WeightItemBean weight_item) {
        this.weight_item = weight_item;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public BmiConBean getBmi_con() {
        return bmi_con;
    }

    public void setBmi_con(BmiConBean bmi_con) {
        this.bmi_con = bmi_con;
    }

    public BmiItemBean getBmi_item() {
        return bmi_item;
    }

    public void setBmi_item(BmiItemBean bmi_item) {
        this.bmi_item = bmi_item;
    }

    public double getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(double bodyfat) {
        this.bodyfat = bodyfat;
    }

    public BodyfatItemBean getBodyfat_item() {
        return bodyfat_item;
    }

    public void setBodyfat_item(BodyfatItemBean bodyfat_item) {
        this.bodyfat_item = bodyfat_item;
    }

    public double getBodyfatrate() {
        return bodyfatrate;
    }

    public void setBodyfatrate(double bodyfatrate) {
        this.bodyfatrate = bodyfatrate;
    }

    public BodyfatrateConBean getBodyfatrate_con() {
        return bodyfatrate_con;
    }

    public void setBodyfatrate_con(BodyfatrateConBean bodyfatrate_con) {
        this.bodyfatrate_con = bodyfatrate_con;
    }

    public BodyfatrateItemBean getBodyfatrate_item() {
        return bodyfatrate_item;
    }

    public void setBodyfatrate_item(BodyfatrateItemBean bodyfatrate_item) {
        this.bodyfatrate_item = bodyfatrate_item;
    }

    public double getFatfreemass() {
        return fatfreemass;
    }

    public void setFatfreemass(double fatfreemass) {
        this.fatfreemass = fatfreemass;
    }

    public FatfreemassConBean getFatfreemass_con() {
        return fatfreemass_con;
    }

    public void setFatfreemass_con(FatfreemassConBean fatfreemass_con) {
        this.fatfreemass_con = fatfreemass_con;
    }

    public FatfreemassItemBean getFatfreemass_item() {
        return fatfreemass_item;
    }

    public void setFatfreemass_item(FatfreemassItemBean fatfreemass_item) {
        this.fatfreemass_item = fatfreemass_item;
    }

    public double getWatercontent() {
        return watercontent;
    }

    public void setWatercontent(double watercontent) {
        this.watercontent = watercontent;
    }

    public WatercontentItemBean getWatercontent_item() {
        return watercontent_item;
    }

    public void setWatercontent_item(WatercontentItemBean watercontent_item) {
        this.watercontent_item = watercontent_item;
    }

    public double getWatercontentrate() {
        return watercontentrate;
    }

    public void setWatercontentrate(double watercontentrate) {
        this.watercontentrate = watercontentrate;
    }

    public WatercontentrateConBean getWatercontentrate_con() {
        return watercontentrate_con;
    }

    public void setWatercontentrate_con(WatercontentrateConBean watercontentrate_con) {
        this.watercontentrate_con = watercontentrate_con;
    }

    public WatercontentrateItemBean getWatercontentrate_item() {
        return watercontentrate_item;
    }

    public void setWatercontentrate_item(WatercontentrateItemBean watercontentrate_item) {
        this.watercontentrate_item = watercontentrate_item;
    }

    public int getVisceralfatindex() {
        return visceralfatindex;
    }

    public void setVisceralfatindex(int visceralfatindex) {
        this.visceralfatindex = visceralfatindex;
    }

    public VisceralfatindexConBean getVisceralfatindex_con() {
        return visceralfatindex_con;
    }

    public void setVisceralfatindex_con(VisceralfatindexConBean visceralfatindex_con) {
        this.visceralfatindex_con = visceralfatindex_con;
    }

    public VisceralfatindexItemBean getVisceralfatindex_item() {
        return visceralfatindex_item;
    }

    public void setVisceralfatindex_item(VisceralfatindexItemBean visceralfatindex_item) {
        this.visceralfatindex_item = visceralfatindex_item;
    }

    public double getBonemass() {
        return bonemass;
    }

    public void setBonemass(double bonemass) {
        this.bonemass = bonemass;
    }

    public BonemassConBean getBonemass_con() {
        return bonemass_con;
    }

    public void setBonemass_con(BonemassConBean bonemass_con) {
        this.bonemass_con = bonemass_con;
    }

    public BonemassItemBean getBonemass_item() {
        return bonemass_item;
    }

    public void setBonemass_item(BonemassItemBean bonemass_item) {
        this.bonemass_item = bonemass_item;
    }

    public double getMusclemass() {
        return musclemass;
    }

    public void setMusclemass(double musclemass) {
        this.musclemass = musclemass;
    }

    public MusclemassConBean getMusclemass_con() {
        return musclemass_con;
    }

    public void setMusclemass_con(MusclemassConBean musclemass_con) {
        this.musclemass_con = musclemass_con;
    }

    public MusclemassItemBean getMusclemass_item() {
        return musclemass_item;
    }

    public void setMusclemass_item(MusclemassItemBean musclemass_item) {
        this.musclemass_item = musclemass_item;
    }

    public int getBasalmetabolicrate() {
        return basalmetabolicrate;
    }

    public void setBasalmetabolicrate(int basalmetabolicrate) {
        this.basalmetabolicrate = basalmetabolicrate;
    }

    public BasalmetabolicrateConBean getBasalmetabolicrate_con() {
        return basalmetabolicrate_con;
    }

    public void setBasalmetabolicrate_con(BasalmetabolicrateConBean basalmetabolicrate_con) {
        this.basalmetabolicrate_con = basalmetabolicrate_con;
    }

    public BasalmetabolicrateItemBean getBasalmetabolicrate_item() {
        return basalmetabolicrate_item;
    }

    public void setBasalmetabolicrate_item(BasalmetabolicrateItemBean basalmetabolicrate_item) {
        this.basalmetabolicrate_item = basalmetabolicrate_item;
    }

    public int getPhysicalage() {
        return physicalage;
    }

    public void setPhysicalage(int physicalage) {
        this.physicalage = physicalage;
    }

    public PhysicalageConBean getPhysicalage_con() {
        return physicalage_con;
    }

    public void setPhysicalage_con(PhysicalageConBean physicalage_con) {
        this.physicalage_con = physicalage_con;
    }

    public PhysicalageItemBean getPhysicalage_item() {
        return physicalage_item;
    }

    public void setPhysicalage_item(PhysicalageItemBean physicalage_item) {
        this.physicalage_item = physicalage_item;
    }

    public static class WeightConBean {
        /**
         * caption : 超重
         * color : ff6666
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class WeightItemBean {
        /**
         * value : 67.6
         * unit : kg
         * color : ff6666
         * arrowcolor : db5151
         * indexdescription : <font color="#ff6666">你的体重超标啦！</font><br />体重是反映和衡量一个人健康状况的重要指标之一，偏瘦或超重都不利于人体健康，也会影响一个人外形的美感。<br />请同时关注你的体脂率。如果体脂率和体重两个指标都高于理想值，说明你有肥胖的倾向；肥胖是各种慢性疾病酝酿的温床！请注意控制热量的摄入，采纳合理的营养搭配，每天运动至少30分钟，循序渐进，让营养顾问帮你制定一个科学的减重计划吧！请记住，减重不等于减肥，减脂才等于减肥哦！
         * range : [{"value":35,"color":"66b3ff"},{"value":52,"color":"93c952"},{"value":57.6,"color":"ff6666"},{"value":125}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBean> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBean> getRange() {
            return range;
        }

        public void setRange(List<RangeBean> range) {
            this.range = range;
        }

        public static class RangeBean {
            /**
             * value : 35
             * color : 66b3ff
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class BmiConBean {
        /**
         * caption : 超重
         * color : ff6666
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class BmiItemBean {
        /**
         * value : 24.8
         * unit : kg/m2
         * color : ee8a17
         * arrowcolor : bd6b0c
         * indexdescription : <font color="#ff6666">你的身材有点超重哦！</font><br />BMI是英文Body Mass Index的缩写，指身体质量指数，是用体重（kg）数除以身高（m）的平方得出的数值。<br />BMI是国际通用的衡量人体胖瘦程度及是否健康的一个标准；当我们需要分析一个人的体重是否标准时，采用BMI指数比单纯分析体重更可靠。<br />请同时关注你的体脂率。如果BMI超重且体脂率高于理想值，你就有肥胖的倾向哦！肥胖是各种慢性疾病酝酿的温床！请一定要注意控制热量的摄入，合理搭配膳食结构，每天运动至少30分钟，循序渐进，让营养顾问帮你制定一个科学的减重计划，健康好身材等着你！
         * range : [{"value":13,"color":"66b3ff"},{"value":18.5,"color":"93c952"},{"value":23,"color":"ee8a17"},{"value":25,"color":"ff6666"},{"value":40}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanX> range) {
            this.range = range;
        }

        public static class RangeBeanX {
            /**
             * value : 13
             * color : 66b3ff
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class BodyfatItemBean {
        /**
         * value : 19.7
         * unit : kg
         * color : 93c952
         * arrowcolor : 7ab138
         * indexdescription : 体脂肪指身体脂肪的总重量。<br />人体脂肪具有保温、储存和提供能量、保护身体组织等功能。因此，体脂肪应当保持在正常范围内，偏低或偏高都不好。偏低会引起身体功能失调，偏高会引起肥胖，肥胖并非指单纯性的体重过高，而是指异常或过量的体脂肪积累，这会增加多种代谢性疾病的患病风险。<br />请参照体脂率解读你的体脂肪状况。
         * range : [{"value":0,"color":"93c952"},{"value":60}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXX> range) {
            this.range = range;
        }

        public static class RangeBeanXX {
            /**
             * value : 0
             * color : 93c952
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class BodyfatrateConBean {
        /**
         * caption : 肥胖
         * color : ff6666
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class BodyfatrateItemBean {
        /**
         * value : 29.1
         * unit : %
         * color : ee8a17
         * arrowcolor : bd6b0c
         * indexdescription : <font color="#ff6666">您的体脂率超标啦！</font><br />体脂率指身体脂肪占身体总体重的百分比，是用来反映身体相对脂肪含量的指标。<br />体脂率低于或高于理想值，代表一个人的体脂肪情况出现异常，这与多种代谢性疾病的发病率有明显关系；体脂率比单纯以体重或BMI作为标准更能反映一个人的肥胖情况，也更适合判断一个人的减肥效果和减肥的方向是否正确；减重不等于减肥，减脂才等于减肥。<br />你的体脂率处于肥胖的范围了。肥胖会大幅增加高血压、II型糖尿病等疾病的患病风险。请立即控制能量摄入，减少高油高糖的食物的摄入，在营养顾问的指导下进行适当的运动，健康身材就在不远处！
         * range : [{"value":6,"color":"66b3ff"},{"value":9.1,"color":"7cc6b3"},{"value":19,"color":"93c952"},{"value":22.5,"color":"f9d133"},{"value":25.5,"color":"ee8a17"},{"value":29.6,"color":"ff6666"},{"value":46}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXX {
            /**
             * value : 6
             * color : 66b3ff
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class FatfreemassConBean {
        /**
         * caption : 理想
         * color : 93c952
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class FatfreemassItemBean {
        /**
         * value : 47.9
         * unit : kg
         * color : 93c952
         * arrowcolor : 7ab138
         * indexdescription : <font color="#93c952">你的去脂体重很理想，身材一定非常健美！</font><br />去脂体重指身体体重扣去体脂肪之后的重量，包括身体水分、蛋白质和无机盐三部分。<br />去脂体重中肌肉和骨骼占很大比重，这是影响身体活动能力的重要因素，测量去脂体重对促进能量转换和耗氧、调节水盐代谢等具有重要意义；通常在运动训练中，运动员保持较高的去脂体重，这对提高有氧耐力和运动能力很有好处；另外，去脂体重与人体的新陈代谢和生长发育密切相关。我们推荐每天每kg去脂体重摄入2g蛋白质，其中1/3到2/3的蛋白质通过膳食补充剂补充。<br />你的去脂体重很理想，这说明你的身体强壮、体质好，一般运动员或平时坚持参加体育锻炼的人，才可能像你一样肌肉强健；尽管你的体重可能不轻，但却看起来健康有力棒棒哒。请继续保持结构合理并且多样化的饮食，继续保持80%营养+20%运动的积极、健康生活方式！健美的好身体，让你充满魅力！
         * range : [{"value":25,"color":"66b3ff"},{"value":38.7,"color":"7cc6b3"},{"value":47.4,"color":"93c952"},{"value":75}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXX {
            /**
             * value : 25
             * color : 66b3ff
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class WatercontentItemBean {
        /**
         * value : 35.2
         * unit : kg
         * color : 93c952
         * arrowcolor : 7ab138
         * indexdescription : 身体水分是人体细胞内水分与细胞外水分的总和。<br />充足的身体水分有助于增加身体代谢循环的速度，增加人体内废物和毒素的排出速度。水分过低或过高都不利于身体的代谢过程，影响身体健康，每天8杯水是维持人体水分平衡的基础。身体水分和肌肉量有着极其密切的关系，这项指标能够反应减重的方式是否正确，如果身体水分下降，不但有损健康，更会令体脂率上升。<br />请参照身体水分率解读你的身体水分状况。
         * range : [{"value":20,"color":"93c952"},{"value":65}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXX {
            /**
             * value : 20
             * color : 93c952
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class WatercontentrateConBean {
        /**
         * caption : 达标
         * color : 93c952
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class WatercontentrateItemBean {
        /**
         * value : 52.1
         * unit : %
         * color : 93c952
         * arrowcolor : 7ab138
         * indexdescription : <font color="#93c952">你的身体水分率已达标，棒棒哒！</font><br />身体水分率是身体水分占身体体重的比值，是反映人体水分相对含量的指标。<br />身体水分不仅构成了身体的主要成分，而且还有许多生理功能，是机体物质代谢必不可少的载体；人体细胞必须从组织间液摄取营养，而营养物质溶于水才能被充分吸收，物质代谢的中间产物和最终产物也必须通过组织间液运送和排除。因此，人体拥有适宜的身体水分率可以维持人体各种代谢活动的正常进行。<br />你的身体水分已达标，你一定有很好的喝水习惯，这对机体的代谢循环很有帮助。请你继续保持结构合理并且多样化的饮食习惯，继续保持80%营养+20%运动的积极、健康生活方式！继续做一名喝水达人吧！
         * range : [{"value":40,"color":"66b3ff"},{"value":51,"color":"93c952"},{"value":65}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXXX {
            /**
             * value : 40
             * color : 66b3ff
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class VisceralfatindexConBean {
        /**
         * caption : 一般
         * color : 93c952
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class VisceralfatindexItemBean {
        /**
         * value : 7.7
         * color : f9d133
         * arrowcolor : e2b607
         * indexdescription : <font color="#93c952">您的内脏脂肪指数状况一般。</font><br />内脏脂肪指数是反映人体脏器（肝脏、胃、肠等器官）周围脂肪含量的指数。<br />由于脂蛋白酶在内脏较皮下更具活性，所以内脏脂肪较易脂解为脂肪微粒并通过血液运送到肝脏；大量的脂肪微粒涌入肝脏，是脂肪肝、高血压、高血脂及高胰岛素形成的主要原因，也容易引起心血管疾病，同时使体内积聚大量的水分。内脏脂肪的增加意味着肥胖和身体老化，特别是会增加各类慢性疾病的发病率，可以用来反映向心性肥胖的程度。较低的内脏脂肪指数可以大幅减少糖尿病、脂肪肝等疾病的发病率。<br />您现在的内脏脂肪指数正常范围内较高的水平，这表明您正处于向心性肥胖的边缘。向心性肥胖会增加脂肪肝、II型糖尿病等的患病风险。建议您注意日常的饮食，控制能量摄入，多进行跑步、骑车等有氧运动。关爱自己，保持健康。
         * range : [{"value":0,"color":"93c952"},{"value":4,"color":"f9d133"},{"value":10,"color":"ee8a17"},{"value":15,"color":"ff6666"},{"value":20}]
         */

        private double value;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXXXX {
            /**
             * value : 0
             * color : 93c952
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class BonemassConBean {
        /**
         * caption : 偏低
         * color : ff6666
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class BonemassItemBean {
        /**
         * value : 2.6
         * unit : kg
         * color : 66b3ff
         * arrowcolor : 4996e1
         * indexdescription : <font color="#93c952">你的骨量偏低！</font><br />骨量是人体骨骼中矿物质的含量，主要由钙、磷等无机盐组成。<br />人体骨骼的强度与骨量的多少密切相关。不同年龄时期骨钙的含量是不同的，因此，峰值骨量就如同人体内的“骨银行”，年轻时峰值骨量越高，相当于在银行中的储蓄越多，可供人们日后消耗的骨量就越多，峰值骨量的多少主要是由遗传因素、营养状况和生活习惯等决定。人体骨量越高就越不容易发生关节炎、骨折和骨质疏松等疾病。<br />骨量偏低容易增加日后患骨质疏松的风险哦！增加骨量，需要补充钙，日常食物中含钙较多的有：牛奶、鸡蛋、豆制品、海带、紫菜、虾皮、海鱼等，特别是牛奶；食用含钙丰富的食物时，应避免过多食用含磷酸盐、草酸丰富的食物，以免影响钙的吸收；建议你多参加户外运动增加体内维生素D的合成，促进钙吸收，同时还需要注意补充胶原蛋白。胶原蛋白是是人体骨骼尤其是软骨组织的重要组成成分。关注好营养，拥抱大自然！
         * range : [{"value":1.5,"color":"66b3ff"},{"value":2.8,"color":"7cc6b3"},{"value":3.5,"color":"93c952"},{"value":5}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXXXXX {
            /**
             * value : 1.5
             * color : 66b3ff
             */

            private double value;
            private String color;

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class MusclemassConBean {
        /**
         * caption : 理想
         * color : 93c952
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class MusclemassItemBean {
        /**
         * value : 45.4
         * unit : kg
         * color : 93c952
         * arrowcolor : 7ab138
         * indexdescription : <font color="#93c952">你的肌肉量状况很理想，你的身材一定非常健美</font><br />肌肉量指人体肌肉的重量，肌肉是人体消耗能量最多的组分。<br />肌肉量与一个人的体质、年龄和运动量等多种因素有关。保证充足的蛋白质摄入，加上合理的运动，可以增加一个人的肌肉量。增加肌肉量不仅可以帮助增加人体能量消耗、提高身体机能，还可以使体型更美观，显得更年轻。<br />你现在的身材很健美，充满力量，可以媲美专业运动员了，让人羡慕哦！不过，随着年龄的增长，当一个人年老时，肌肉的力量会衰退，所以，保持运动习惯特别是肌肉训练的习惯应该是人生中应该长期坚持的事情。请继续保持结构合理并且多样化的饮食习惯，继续保持80%营养+20%运动的积极、健康生活方式！健美好身材，成就逆生长！
         * range : [{"value":25,"color":"66b3ff"},{"value":36.5,"color":"7cc6b3"},{"value":44.8,"color":"93c952"},{"value":75}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXXXXXX {
            /**
             * value : 25
             * color : 66b3ff
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }

    public static class BasalmetabolicrateConBean {
        /**
         * caption : 代谢偏慢
         * color : ff6666
         */

        private String caption;
        private String color;

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class BasalmetabolicrateItemBean {
        /**
         * value : 1405.5
         * unit : kcal/day
         * color : 7cc6b3
         * arrowcolor : 5fb59f
         * indexdescription : <font color="#ff6666">你的基础代谢偏慢！</font><br />基础代谢指人体每天维持基础的生命活动（包括心跳、呼吸和体温等）所需要的能量。基础代谢受肌肉活动、环境温度、食物摄入或精神紧张等因素影响。<br />你现在看见的基础代谢不是根据你的体重或BMI推算出来，而是根据你的体成分测量出来的，所以更准确。基础代谢越高，意味着不运动的状态下身体自身消耗的能量越高。你的基础代谢与你的肌肉量有密切关系。合理饮食，增加运动，有利于提高自身的基础代谢水平，也就更不容易长胖啦。<br />你的基础代谢略微有点低，如果增加更多的肌肉，你的基础代谢会提高至正常水平，这意味着你更不容易发胖！请继续保持营养结构合理、品类多样化的饮食习惯，保持并适度增加运动量！
         * range : [{"value":985.7,"color":"66b3ff","valuetip":"70%"},{"value":1126.5,"color":"5dc3e1","valuetip":"80%"},{"value":1267.4,"color":"7cc6b3","valuetip":"90%"},{"value":1408.2,"color":"93c952","valuetip":"100%"},{"value":1549,"valuetip":"110%"}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXXXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXXXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXXXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXXXXXXX {
            /**
             * value : 985.7
             * color : 66b3ff
             * valuetip : 70%
             */

            private double value;
            private String color;
            private String valuetip;

            public double getValue() {
                return value;
            }

            public void setValue(double value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getValuetip() {
                return valuetip;
            }

            public void setValuetip(String valuetip) {
                this.valuetip = valuetip;
            }
        }
    }

    public static class PhysicalageConBean {
        /**
         * color : ff6666
         */

        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class PhysicalageItemBean {
        /**
         * value : 29.3
         * unit :
         * color : ff6666
         * arrowcolor : db5151
         * indexdescription : 身体年龄是当前身体状况所对应的生理年龄，判断标准是与实际年龄进行比较。<br />身体年龄越小意味着身体情况越健康，身体机能更年轻。身体年龄越大意味着身体情况越不健康，身体机能偏老化。<br />健康的身体，拥有小于实际年龄10-20岁的身体年龄。请养成结构合理的多样化饮食习惯，继续保持80%营养+20%运动的积极、健康生活方式，努力朝着更年轻的方向迈进吧！
         * range : [{"value":0,"color":"93c952"},{"value":19,"color":"ff6666"},{"value":59}]
         */

        private double value;
        private String unit;
        private String color;
        private String arrowcolor;
        private String indexdescription;
        private List<RangeBeanXXXXXXXXXXX> range;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getArrowcolor() {
            return arrowcolor;
        }

        public void setArrowcolor(String arrowcolor) {
            this.arrowcolor = arrowcolor;
        }

        public String getIndexdescription() {
            return indexdescription;
        }

        public void setIndexdescription(String indexdescription) {
            this.indexdescription = indexdescription;
        }

        public List<RangeBeanXXXXXXXXXXX> getRange() {
            return range;
        }

        public void setRange(List<RangeBeanXXXXXXXXXXX> range) {
            this.range = range;
        }

        public static class RangeBeanXXXXXXXXXXX {
            /**
             * value : 0
             * color : 93c952
             */

            private int value;
            private String color;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }
    }
}