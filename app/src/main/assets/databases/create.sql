drop table if exists Category;
drop table if exists Item;

create table Category(
   name text primary key
);

create table Item(
   id integer primary key autoincrement,
   name text not null unique,
   category text references Category(name)
);

insert into Category(name) values('Produce');
insert into Category(name) values('Dairy');
insert into Category(name) values('Baked Goods');
insert into Category(name) values('Baking');
insert into Category(name) values('Beverages');
insert into Category(name) values('Canned Foods');
insert into Category(name) values('Cheese');
insert into Category(name) values('Condiments');
insert into Category(name) values('Dairy');
insert into Category(name) values('Frozen Foods');
insert into Category(name) values('Meats');
insert into Category(name) values('Produce');
insert into Category(name) values('Refrigerated Items');
insert into Category(name) values('Seafood');
insert into Category(name) values('Snacks');
insert into Category(name) values('Spices & Herbs');

insert into Item(name, category) values('potato', 'Produce');
insert into Item(name, category) values('tomato', 'Produce');
insert into Item(name, category) values('carrot', 'Produce');
insert into Item(name, category) values('milk', 'Dairy');
insert into Item(name, category) values('cheese', 'Dairy');

insert into Item(name, category) values('bagel', 'Baked Goods');
insert into Item(name, category) values('bread', 'Baked Goods');
insert into Item(name, category) values('bun', 'Baked Goods');
insert into Item(name, category) values('cake', 'Baked Goods');
insert into Item(name, category) values('cookie', 'Baked Goods');
insert into Item(name, category) values('croissant', 'Baked Goods');
insert into Item(name, category) values('donut', 'Baked Goods');
insert into Item(name, category) values('pastry', 'Baked Goods');
insert into Item(name, category) values('pie', 'Baked Goods');
insert into Item(name, category) values('roll', 'Baked Goods');
insert into Item(name, category) values('tortilla', 'Baked Goods');

insert into Item(name, category) values('baking powder', 'Baking');
insert into Item(name, category) values('baking soda', 'Baking');
insert into Item(name, category) values('bread crumbs', 'Baking');
insert into Item(name, category) values('brownie mix', 'Baking');
insert into Item(name, category) values('cake mix', 'Baking');
insert into Item(name, category) values('chocolate chips', 'Baking');
insert into Item(name, category) values('cocoa', 'Baking');
insert into Item(name, category) values('flour', 'Baking');
insert into Item(name, category) values('icing', 'Baking');
insert into Item(name, category) values('shortening', 'Baking');
insert into Item(name, category) values('sugar', 'Baking');
insert into Item(name, category) values('yeast', 'Baking');

insert into Item(name, category) values('beer', 'Beverages');
insert into Item(name, category) values('champagne', 'Beverages');
insert into Item(name, category) values('club soda', 'Beverages');
insert into Item(name, category) values('gin', 'Beverages');
insert into Item(name, category) values('juice', 'Beverages');
insert into Item(name, category) values('pop', 'Beverages');
insert into Item(name, category) values('red wine', 'Beverages');
insert into Item(name, category) values('rum', 'Beverages');
insert into Item(name, category) values('soda', 'Beverages');
insert into Item(name, category) values('sports drink', 'Beverages');
insert into Item(name, category) values('tonic', 'Beverages');
insert into Item(name, category) values('vodka', 'Beverages');
insert into Item(name, category) values('whiskey', 'Beverages');
insert into Item(name, category) values('white wine', 'Beverages');

insert into Item(name, category) values('applesauce', 'Canned Foods');
insert into Item(name, category) values('beans', 'Canned Foods');
insert into Item(name, category) values('broth', 'Canned Foods');
insert into Item(name, category) values('canned', 'Canned Foods');
insert into Item(name, category) values('chili', 'Canned Foods');
insert into Item(name, category) values('olives', 'Canned Foods');
insert into Item(name, category) values('soup', 'Canned Foods');
insert into Item(name, category) values('tuna', 'Canned Foods');

insert into Item(name, category) values('bleu', 'Cheese');
insert into Item(name, category) values('cheddar', 'Cheese');
insert into Item(name, category) values('cottage', 'Cheese');
insert into Item(name, category) values('cream cheese', 'Cheese');
insert into Item(name, category) values('feta', 'Cheese');
insert into Item(name, category) values('mozzarella', 'Cheese');
insert into Item(name, category) values('parmesan', 'Cheese');
insert into Item(name, category) values('prvolone', 'Cheese');
insert into Item(name, category) values('rocotta', 'Cheese');
insert into Item(name, category) values('swiss', 'Cheese');

insert into Item(name, category) values('bbq sauce', 'Condiments');
insert into Item(name, category) values('dressing', 'Condiments');
insert into Item(name, category) values('gravy', 'Condiments');
insert into Item(name, category) values('honey', 'Condiments');
insert into Item(name, category) values('hot sauce', 'Condiments');
insert into Item(name, category) values('jam', 'Condiments');
insert into Item(name, category) values('jelly', 'Condiments');
insert into Item(name, category) values('ketchup', 'Condiments');
insert into Item(name, category) values('mayonnaise', 'Condiments');
insert into Item(name, category) values('mayo', 'Condiments');
insert into Item(name, category) values('mustard', 'Condiments');
insert into Item(name, category) values('pasta suce', 'Condiments');
insert into Item(name, category) values('preserves', 'Condiments');
insert into Item(name, category) values('relish', 'Condiments');
insert into Item(name, category) values('salsa', 'Condiments');
insert into Item(name, category) values('soy sauce', 'Condiments');
insert into Item(name, category) values('steak sauce', 'Condiments');
insert into Item(name, category) values('syrup', 'Condiments');
insert into Item(name, category) values('worcestershire', 'Condiments');

insert into Item(name, category) values('butter', 'Dairy');
insert into Item(name, category) values('cheese', 'Dairy');
insert into Item(name, category) values('cottage cheese', 'Dairy');
insert into Item(name, category) values('half and half', 'Dairy');
insert into Item(name, category) values('margarine', 'Dairy');
insert into Item(name, category) values('milk', 'Dairy');
insert into Item(name, category) values('sour cream', 'Dairy');
insert into Item(name, category) values('whipped cream', 'Dairy');
insert into Item(name, category) values('yogurt', 'Dairy');

insert into Item(name, category) values('fish sticks', 'Frozen Foods');
insert into Item(name, category) values('fries', 'Frozen Foods');
insert into Item(name, category) values('ice cream', 'Frozen Foods');
insert into Item(name, category) values('juice concentrate', 'Frozen Foods');
insert into Item(name, category) values('pizza', 'Frozen Foods');
insert into Item(name, category) values('popsicle', 'Frozen Foods');
insert into Item(name, category) values('sorbet', 'Frozen Foods');
insert into Item(name, category) values('tater tots', 'Frozen Foods');
insert into Item(name, category) values('tv dinner', 'Frozen Foods');

insert into Item(name, category) values('bacon', 'Meat');
insert into Item(name, category) values('beef', 'Meat');
insert into Item(name, category) values('chicken', 'Meat');
insert into Item(name, category) values('ham', 'Meat');
insert into Item(name, category) values('hot dog', 'Meat');
insert into Item(name, category) values('lunchmeat', 'Meat');
insert into Item(name, category) values('pork', 'Meat');
insert into Item(name, category) values('sausage', 'Meat');
insert into Item(name, category) values('turkey', 'Meat');

insert into Item(name, category) values('apple', 'Produce');
insert into Item(name, category) values('asparagus', 'Produce');
insert into Item(name, category) values('avocado', 'Produce');
insert into Item(name, category) values('banana', 'Produce');
insert into Item(name, category) values('berry', 'Produce');
insert into Item(name, category) values('broccoli', 'Produce');
insert into Item(name, category) values('carrot', 'Produce');
insert into Item(name, category) values('cauliflower', 'Produce');
insert into Item(name, category) values('celery', 'Produce');
insert into Item(name, category) values('cherry', 'Produce');
insert into Item(name, category) values('corn cucumbers', 'Produce');
insert into Item(name, category) values('grape', 'Produce');
insert into Item(name, category) values('grapefruit', 'Produce');
insert into Item(name, category) values('kiwi', 'Produce');
insert into Item(name, category) values('lemon', 'Produce');
insert into Item(name, category) values('lettuce', 'Produce');
insert into Item(name, category) values('lime', 'Produce');
insert into Item(name, category) values('melon', 'Produce');
insert into Item(name, category) values('mushroom', 'Produce');
insert into Item(name, category) values('nectarine', 'Produce');
insert into Item(name, category) values('onion', 'Produce');
insert into Item(name, category) values('orange', 'Produce');
insert into Item(name, category) values('peach', 'Produce');
insert into Item(name, category) values('pear', 'Produce');
insert into Item(name, category) values('peppers', 'Produce');
insert into Item(name, category) values('plum', 'Produce');
insert into Item(name, category) values('potato', 'Produce');
insert into Item(name, category) values('spinach', 'Produce');
insert into Item(name, category) values('squash', 'Produce');
insert into Item(name, category) values('tomato', 'Produce');
insert into Item(name, category) values('zucchini', 'Produce');

insert into Item(name, category) values('chip dip', 'Refrigerated Items');
insert into Item(name, category) values('eggs', 'Refrigerated Items');
insert into Item(name, category) values('english muffins', 'Refrigerated Items');
insert into Item(name, category) values('hummus', 'Refrigerated Items');
insert into Item(name, category) values('tofu', 'Refrigerated Items');

insert into Item(name, category) values('catfish', 'Seafood');
insert into Item(name, category) values('crab lobster', 'Seafood');
insert into Item(name, category) values('mussels', 'Seafood');
insert into Item(name, category) values('oysters', 'Seafood');
insert into Item(name, category) values('salmon', 'Seafood');
insert into Item(name, category) values('shrimp', 'Seafood');
insert into Item(name, category) values('tilapia', 'Seafood');

insert into Item(name, category) values('candy', 'Snacks');
insert into Item(name, category) values('chips', 'Snacks');
insert into Item(name, category) values('cookie', 'Snacks');
insert into Item(name, category) values('cracker', 'Snacks');
insert into Item(name, category) values('dried fruit', 'Snacks');
insert into Item(name, category) values('granola bar', 'Snacks');
insert into Item(name, category) values('gum nuts', 'Snacks');
insert into Item(name, category) values('oatmeal', 'Snacks');
insert into Item(name, category) values('popcorn', 'Snacks');
insert into Item(name, category) values('pretzel', 'Snacks');
insert into Item(name, category) values('seeds', 'Snacks');

insert into Item(name, category) values('basil', 'Spices & Herbs');
insert into Item(name, category) values('cilatro', 'Spices & Herbs');
insert into Item(name, category) values('cinnmon', 'Spices & Herbs');
insert into Item(name, category) values('garlic ginger', 'Spices & Herbs');
insert into Item(name, category) values('mint', 'Spices & Herbs');
insert into Item(name, category) values('oregano', 'Spices & Herbs');
insert into Item(name, category) values('paprika', 'Spices & Herbs');
insert into Item(name, category) values('parsley', 'Spices & Herbs');
insert into Item(name, category) values('pepper', 'Spices & Herbs');
insert into Item(name, category) values('salt', 'Spices & Herbs');
insert into Item(name, category) values('vanilla', 'Spices & Herbs');
