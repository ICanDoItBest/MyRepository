package com.fourapeteam.snacksmall.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class HomeItemBean {

    /**
     * rs_code : 1000
     * data : {"count":5,"items":[{"id":8183,"title":"[韩国三养]超辣火鸡面140gx5连包 辣到爽","sales_title":"","type":0,"guide_type":0,"status":0,"sold_num":0,"surplus_num":0,"price":{"current":36.9,"prime":45},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/5b3/b3/3/1e39207e8bdc2d4d10f81f65b72c15b3.jpg","img_w":612,"img_h":340},"freight":0,"time":1483773420,"fav_num":0,"desc":"","sub_classify_id":0},{"id":8735,"title":"[英国Twinings] 川宁金毫英式早餐红茶","sales_title":"","type":0,"guide_type":0,"status":0,"sold_num":0,"surplus_num":0,"price":{"current":56,"prime":72},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/7d7/d7/7/51288958ce744ac555613da3bb7677d7.jpg","img_w":612,"img_h":340},"freight":0,"time":1485501600,"fav_num":0,"desc":"","sub_classify_id":0},{"id":8226,"title":"[美国纷时乐] 蜂蜜烤碧根果仁 美式风味","sales_title":"","type":2,"guide_type":0,"status":0,"sold_num":0,"surplus_num":0,"price":{"current":16.89,"prime":49.8},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/17b/7b/b/8c5ec33a0575172d263733c49862417b.jpg","img_w":612,"img_h":340},"freight":0,"time":1483169220,"fav_num":0,"desc":"","sub_classify_id":0},{"id":9080,"title":"[美国杰乐] Jell-O  果冻粉85g(草莓味)  DIY简单健康","sales_title":"","type":2,"guide_type":0,"status":1,"sold_num":0,"surplus_num":0,"price":{"current":9.8,"prime":29.8},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/422/22/2/fcc10af3e6cbb2b7cffcda2a08ba1422.jpg","img_w":612,"img_h":340},"freight":0,"time":1480750200,"fav_num":0,"desc":"","sub_classify_id":0},{"id":8225,"title":"[美国纷时乐]美式蜂蜜烤什锦坚果仁","sales_title":"","type":2,"guide_type":0,"status":1,"sold_num":0,"surplus_num":0,"price":{"current":11.9,"prime":48.9},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/b7b/7b/b/119d0a77b3e857ff921cce81b8970b7b.jpg","img_w":612,"img_h":340},"freight":0,"time":1480490760,"fav_num":0,"desc":"","sub_classify_id":0}]}
     * rs_msg : success
     */

    private String rs_code;
    /**
     * count : 5
     * items : [{"id":8183,"title":"[韩国三养]超辣火鸡面140gx5连包 辣到爽","sales_title":"","type":0,"guide_type":0,"status":0,"sold_num":0,"surplus_num":0,"price":{"current":36.9,"prime":45},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/5b3/b3/3/1e39207e8bdc2d4d10f81f65b72c15b3.jpg","img_w":612,"img_h":340},"freight":0,"time":1483773420,"fav_num":0,"desc":"","sub_classify_id":0},{"id":8735,"title":"[英国Twinings] 川宁金毫英式早餐红茶","sales_title":"","type":0,"guide_type":0,"status":0,"sold_num":0,"surplus_num":0,"price":{"current":56,"prime":72},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/7d7/d7/7/51288958ce744ac555613da3bb7677d7.jpg","img_w":612,"img_h":340},"freight":0,"time":1485501600,"fav_num":0,"desc":"","sub_classify_id":0},{"id":8226,"title":"[美国纷时乐] 蜂蜜烤碧根果仁 美式风味","sales_title":"","type":2,"guide_type":0,"status":0,"sold_num":0,"surplus_num":0,"price":{"current":16.89,"prime":49.8},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/17b/7b/b/8c5ec33a0575172d263733c49862417b.jpg","img_w":612,"img_h":340},"freight":0,"time":1483169220,"fav_num":0,"desc":"","sub_classify_id":0},{"id":9080,"title":"[美国杰乐] Jell-O  果冻粉85g(草莓味)  DIY简单健康","sales_title":"","type":2,"guide_type":0,"status":1,"sold_num":0,"surplus_num":0,"price":{"current":9.8,"prime":29.8},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/422/22/2/fcc10af3e6cbb2b7cffcda2a08ba1422.jpg","img_w":612,"img_h":340},"freight":0,"time":1480750200,"fav_num":0,"desc":"","sub_classify_id":0},{"id":8225,"title":"[美国纷时乐]美式蜂蜜烤什锦坚果仁","sales_title":"","type":2,"guide_type":0,"status":1,"sold_num":0,"surplus_num":0,"price":{"current":11.9,"prime":48.9},"img":{"img_url":"http://img.lingshi.cccwei.com/lingshi/b7b/7b/b/119d0a77b3e857ff921cce81b8970b7b.jpg","img_w":612,"img_h":340},"freight":0,"time":1480490760,"fav_num":0,"desc":"","sub_classify_id":0}]
     */

    private DataBean data;
    private String rs_msg;

    public String getRs_code() {
        return rs_code;
    }

    public void setRs_code(String rs_code) {
        this.rs_code = rs_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getRs_msg() {
        return rs_msg;
    }

    public void setRs_msg(String rs_msg) {
        this.rs_msg = rs_msg;
    }

    public static class DataBean {
        private int count;
        /**
         * id : 8183
         * title : [韩国三养]超辣火鸡面140gx5连包 辣到爽
         * sales_title :
         * type : 0
         * guide_type : 0
         * status : 0
         * sold_num : 0
         * surplus_num : 0
         * price : {"current":36.9,"prime":45}
         * img : {"img_url":"http://img.lingshi.cccwei.com/lingshi/5b3/b3/3/1e39207e8bdc2d4d10f81f65b72c15b3.jpg","img_w":612,"img_h":340}
         * freight : 0
         * time : 1483773420
         * fav_num : 0
         * desc :
         * sub_classify_id : 0
         */

        private List<ItemsBean> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private int id;
            private String title;
            private String sales_title;
            private int type;
            private int guide_type;
            private int status;
            private int sold_num;
            private int surplus_num;
            /**
             * current : 36.9
             * prime : 45
             */

            private PriceBean price;
            /**
             * img_url : http://img.lingshi.cccwei.com/lingshi/5b3/b3/3/1e39207e8bdc2d4d10f81f65b72c15b3.jpg
             * img_w : 612
             * img_h : 340
             */

            private ImgBean img;
            private int freight;
            private int time;
            private int fav_num;
            private String desc;
            private int sub_classify_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSales_title() {
                return sales_title;
            }

            public void setSales_title(String sales_title) {
                this.sales_title = sales_title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getGuide_type() {
                return guide_type;
            }

            public void setGuide_type(int guide_type) {
                this.guide_type = guide_type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSold_num() {
                return sold_num;
            }

            public void setSold_num(int sold_num) {
                this.sold_num = sold_num;
            }

            public int getSurplus_num() {
                return surplus_num;
            }

            public void setSurplus_num(int surplus_num) {
                this.surplus_num = surplus_num;
            }

            public PriceBean getPrice() {
                return price;
            }

            public void setPrice(PriceBean price) {
                this.price = price;
            }

            public ImgBean getImg() {
                return img;
            }

            public void setImg(ImgBean img) {
                this.img = img;
            }

            public int getFreight() {
                return freight;
            }

            public void setFreight(int freight) {
                this.freight = freight;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getFav_num() {
                return fav_num;
            }

            public void setFav_num(int fav_num) {
                this.fav_num = fav_num;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getSub_classify_id() {
                return sub_classify_id;
            }

            public void setSub_classify_id(int sub_classify_id) {
                this.sub_classify_id = sub_classify_id;
            }

            public static class PriceBean {
                private double current;
                private double prime;

                public double getCurrent() {
                    return current;
                }

                public void setCurrent(double current) {
                    this.current = current;
                }

                public double getPrime() {
                    return prime;
                }

                public void setPrime(double prime) {
                    this.prime = prime;
                }
            }

            public static class ImgBean {
                private String img_url;
                private int img_w;
                private int img_h;

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }

                public int getImg_w() {
                    return img_w;
                }

                public void setImg_w(int img_w) {
                    this.img_w = img_w;
                }

                public int getImg_h() {
                    return img_h;
                }

                public void setImg_h(int img_h) {
                    this.img_h = img_h;
                }
            }

            @Override
            public String toString() {
                return "ItemsBean{" +
                        "title='" + title + '\'' +
                        '}';
            }
        }
    }
}
