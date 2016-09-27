package com.example.administrator.fruitcuttersimple.bean;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.administrator.fruitcuttersimple.normalgamefruitcutted.AnySurfaceView;
import com.example.administrator.fruitcuttersimple.normalgamefruitcutted.BitmapGroup;
import com.example.administrator.fruitcuttersimple.normalgamefruitcutted.RandomGenerator;

import java.util.List;

/**
 * 类描述：首页游戏
 * 创建人：quzongyang
 * 创建时间：2016/9/7. 19:39
 * 版本：
 */
public class GameResultEntity extends BaseEntity{

    /**
     * code : 0
     * message : 游戏加载成功
     */

    private StatusEntity status;
    /**
     * id : 278
     * type : 2
     * interval : 2
     * item : [{"img":"upload/2016/08/31/20160831030122397.png","click_img":"upload/2016/08/31/20160831030132906.png","click_type":1,"speed":1,"reward":{"coupon":{"ctype":4,"pay_value":1,"restrictive":15,"code":"red_packet","typename":"满15减1元","cname":"1元代金券","showname":"1","is_all":"0"}}},{"img":"upload/2016/08/31/20160831030122397.png","click_img":"upload/2016/08/31/20160831030132906.png","click_type":3,"speed":2,"reward":{"coupon":{"ctype":4,"pay_value":2,"restrictive":25,"code":"red_packet","typename":"满25减2元","cname":"2元代金券","showname":"2","is_all":"0"}}},{"img":"upload/2016/08/31/20160831030122397.png","click_img":"upload/2016/08/31/20160831030132906.png","click_type":3,"speed":3,"reward":{"coupon":{"ctype":4,"pay_value":3,"restrictive":35,"code":"red_packet","typename":"满35减3元","cname":"3元代金券","showname":"3","is_all":"0"}}}]
     * back_img : upload/2016/08/02/20160802114312185.png
     * add_time : 2016-09-07 13:42:45
     * is_del : 0
     * desc : 打地鼠
     */

    private GameEntity data;

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public GameEntity getData() {
        return data;
    }

    public void setData(GameEntity data) {
        this.data = data;
    }


    public static class GameEntity extends BaseEntity{
        private String id;
        private String type;
        private int interval;
        private String back_img;
        private String add_time;
        private String is_del;
        private String desc;
        private int over_type;//1积分   2炸弹结束
        private String other_img;

        public String getOther_img() {
            return other_img;
        }

        public void setOther_img(String other_img) {
            this.other_img = other_img;
        }

        public int getOver_type() {
            return over_type;
        }

        public void setOver_type(int over_type) {
            this.over_type = over_type;
        }

        /**
         * img : upload/2016/08/31/20160831030122397.png
         * click_img : upload/2016/08/31/20160831030132906.png
         * click_type : 1
         * speed : 1
         * reward : {"coupon":{"ctype":4,"pay_value":1,"restrictive":15,"code":"red_packet","typename":"满15减1元","cname":"1元代金券","showname":"1","is_all":"0"}}
         */


        private List<GameItemEntity> item;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public String getBack_img() {
            return back_img;
        }

        public void setBack_img(String back_img) {
            this.back_img = back_img;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<GameItemEntity> getItem() {
            return item;
        }

        public void setItem(List<GameItemEntity> item) {
            this.item = item;
        }

        public static class GameItemEntity extends BaseEntity{
            private int index;
            private String img;
            private String click_img;
            private int click_type;
            private int speed;
            private int is_bomb;//1 炸弹  0红包
            private float height;
            private float width;
            private String reward_type;
            private String reward_value;
            //-----------------start
            //加速度
            private final static float accelerateY = 3;
            //切前的水果Bitmap
            private Bitmap fruitBitmap;
            //切后的水果Bitmap
            private Bitmap fruitBitmapOne;
            private Bitmap fruitBitmapTwo;
            //声明随机数生成器
            private RandomGenerator random;
            //水果位置
            private Point pos;
            //水果速度
            private Point velocity;
            //获得水果bitmap的大小
            private Point size;
            //水果是否被切中
            private boolean isCUT = false;
            //被切后执行一次的状态参数
            private boolean executeOnceStatus = false;
            //被切后另一半水果的速度和位置
            private Point posTwo;
            private Point velocityTwo;
            public int position;

            //切中之前 -- 改变图片坐标（移动）
            private void resetPos(){
                pos.x += velocity.x;
                pos.y += velocity.y;

                velocity.y += accelerateY;
            }

            //切中之后 -- 改变图片坐标
            public void resetPosAfterCut(){
                posTwo.x += velocityTwo.x;
                posTwo.y += velocityTwo.y;

                velocityTwo.y += accelerateY;
            }

            //绘制
            public void drawBeforeCut( Canvas canvas ){
                //切中之前
                if( !isCUT ){
                    resetPos();

                    canvas.drawBitmap( fruitBitmap, pos.x, pos.y, null );
                } else {
                    if( !executeOnceStatus ){
                        executeOnce();
                    }
                    resetPos();
                    resetPosAfterCut();

                    canvas.drawBitmap( fruitBitmapOne, pos.x, pos.y, null );
                    canvas.drawBitmap( fruitBitmapTwo, posTwo.x, posTwo.y, null );
                }

            }

            //被切后执行一次
            private void executeOnce(){
                AnySurfaceView.cutCount ++;

                if( velocity.x>0 ){
                    velocity.x -= 3;
                } else {
                    velocity.x += 3;
                }

                if( velocity.y<0 ){
                    velocity.y += 3;
                }

                posTwo = new Point( pos.x, pos.y + 5 );
                velocityTwo = new Point( velocity.x, velocity.y + 1 );

                executeOnceStatus = true;
            }

            public void setCUT( boolean isCUT ){
                this.isCUT = isCUT;
            }

            public boolean getCUT(){
                return isCUT;
            }

            //返回当前位置
            public Point getBitmapPos(){
                return pos;
            }

            public Point getBitmapSize(){
                return size;
            }

            public GameItemEntity(){}

            public void setBitMapResource(BitmapGroup bitmapGroup, int index){
                fruitBitmap = bitmapGroup.getFruitBitmap(index);

                fruitBitmapOne = bitmapGroup.getFruitBitmapOne(index);
                fruitBitmapTwo = bitmapGroup.getFruitBitmapTwo(index);

                random = new RandomGenerator();

                pos = random.getPos();

                velocity = random.getVelocity();

                size = new Point( fruitBitmap.getWidth(), fruitBitmap.getHeight() );

                // System.out.println( "=========" + numOfFruitType + "============" + currentFruitIndex );
            }

            //构造方法
           /* public GameItemEntity(){

                fruitBitmap = bitmapGroup.getFruitBitmap(index);

                fruitBitmapOne = bitmapGroup.getFruitBitmapOne(index);
                fruitBitmapTwo = bitmapGroup.getFruitBitmapTwo(index);

                random = new RandomGenerator();

                pos = random.getPos();

                velocity = random.getVelocity();

                size = new Point( fruitBitmap.getWidth(), fruitBitmap.getHeight() );

                // System.out.println( "=========" + numOfFruitType + "============" + currentFruitIndex );
            }*/
            //-----------------end

            public float getHeight() {
                return height;
            }

            public void setHeight(float height) {
                this.height = height;
            }

            public float getWidth() {
                return width;
            }

            public void setWidth(float width) {
                this.width = width;
            }

            public String getReward_type() {
                return reward_type;
            }

            public void setReward_type(String reward_type) {
                this.reward_type = reward_type;
            }

            public String getReward_value() {
                return reward_value;
            }

            public void setReward_value(String reward_value) {
                this.reward_value = reward_value;
            }

            /**
             * coupon : {"ctype":4,"pay_value":1,"restrictive":15,"code":"red_packet","typename":"满15减1元","cname":"1元代金券","showname":"1","is_all":"0"}
             */

            //private RewardBean reward;

            public int getIs_bomb() {
                return is_bomb;
            }

            public void setIs_bomb(int is_bomb) {
                this.is_bomb = is_bomb;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getClick_img() {
                return click_img;
            }

            public void setClick_img(String click_img) {
                this.click_img = click_img;
            }

            public int getClick_type() {
                return click_type;
            }

            public void setClick_type(int click_type) {
                this.click_type = click_type;
            }

            public int getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
            }

            /*public RewardBean getReward() {
                return reward;
            }

            public void setReward(RewardBean reward) {
                this.reward = reward;
            }*/

            /*public static class RewardBean extends BaseEntity{
                public String type;
                public int coupon_id;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getCoupon_id() {
                    return coupon_id;
                }

                public void setCoupon_id(int coupon_id) {
                    this.coupon_id = coupon_id;
                }


            }*/
        }
    }
}
