
INSERT INTO `multitenancy`.`common_num` (`id`, `count_rows`, `name`) VALUES ('1', '0', 'ORGANISATION_MASTER');
INSERT INTO `multitenancy`.`common_num` (`id`, `count_rows`, `name`) VALUES ('2', '0', 'SITE_MASTER');


INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('1' ,  'Europe');
INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('2' ,  'Arab States');
INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('3' ,  'Asia & Pacific');
INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('4' ,  'South/Latin America');
INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('5' ,  'CIS');
INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('6' ,  'Africa');
INSERT INTO `region` (`region_Id`,`region_Name`) VALUES ('7' ,  'North America');


INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('1', '93', 'Afghanistan', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('10', '54', 'Argentina', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('100', '354', 'Iceland', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('101', '91', 'India', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('102', '62', 'Indonesia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('103', '98', 'Iran', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('104', '964', 'Iraq', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('105', '353', 'Ireland', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('106', '972', 'Israel', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('107', '39', 'Italy', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('108', '1876', 'Jamaica', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('109', '81', 'Japan', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('11', '374', 'Armenia', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('110', '44', 'Jersey', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('111', '962', 'Jordan', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('112', '2', 'Kazakhstan', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('113', '254', 'Kenya', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('114', '686', 'Kiribati', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('115', '850', 'Korea North', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('116', '82', 'Korea South', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('117', '965', 'Kuwait', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('118', '996', 'Kyrgyzstan', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('119', '856', 'Laos', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('12', '297', 'Aruba', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('120', '371', 'Latvia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('121', '961', 'Lebanon', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('122', '266', 'Lesotho', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('123', '231', 'Liberia', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('124', '218', 'Libya', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('125', '423', 'Liechtenstein', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('126', '370', 'Lithuania', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('127', '352', 'Luxembourg', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('128', '853', 'Macau S.A.R.', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('129', '389', 'Macedonia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('13', '61', 'Australia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('130', '261', 'Madagascar', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('131', '265', 'Malawi', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('132', '60', 'Malaysia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('133', '960', 'Maldives', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('134', '223', 'Mali', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('135', '356', 'Malta', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('136', '44', 'Man (Isle of)', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('137', '692', 'Marshall Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('138', '596', 'Martinique', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('139', '222', 'Mauritania', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('14', '43', 'Austria', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('140', '230', 'Mauritius', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('141', '269', 'Mayotte', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('142', '52', 'Mexico', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('143', '691', 'Micronesia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('144', '373', 'Moldova', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('145', '377', 'Monaco', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('146', '976', 'Mongolia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('147', '1664', 'Montserrat', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('148', '212', 'Morocco', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('149', '258', 'Mozambique', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('15', '994', 'Azerbaijan', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('150', '95', 'Myanmar', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('151', '264', 'Namibia', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('152', '674', 'Nauru', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('153', '977', 'Nepal', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('154', '599', 'Netherlands Antilles', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('155', '31', 'Netherlands The', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('156', '687', 'New Caledonia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('157', '64', 'New Zealand', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('158', '505', 'Nicaragua', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('159', '227', 'Niger', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('16', '1242', 'Bahamas The', '7');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('160', '234', 'Nigeria', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('161', '683', 'Niue', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('162', '672', 'Norfolk Island', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('163', '1670', 'Northern Mariana Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('164', '47', 'Norway', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('165', '968', 'Oman', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('166', '92', 'Pakistan', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('167', '680', 'Palau', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('168', '970', 'Palestinian Territory Occupied', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('169', '507', 'Panama', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('17', '973', 'Bahrain', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('170', '675', 'Papua new Guinea', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('171', '595', 'Paraguay', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('172', '51', 'Peru', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('173', '63', 'Philippines', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('174', '0', 'Pitcairn Island', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('175', '48', 'Poland', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('176', '351', 'Portugal', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('177', '1787', 'Puerto Rico', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('178', '974', 'Qatar', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('179', '262', 'Reunion', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('18', '880', 'Bangladesh', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('180', '40', 'Romania', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('181', '70', 'Russia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('182', '250', 'Rwanda', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('183', '290', 'Saint Helena', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('184', '1869', 'Saint Kitts And Nevis', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('185', '1758', 'Saint Lucia', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('186', '508', 'Saint Pierre and Miquelon', '7');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('187', '1784', 'Saint Vincent And The Grenadines', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('188', '684', 'Samoa', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('189', '378', 'San Marino', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('19', '1246', 'Barbados', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('190', '239', 'Sao Tome and Principe', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('191', '966', 'Saudi Arabia', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('192', '221', 'Senegal', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('193', '381', 'Serbia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('194', '248', 'Seychelles', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('195', '232', 'Sierra Leone', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('196', '65', 'Singapore', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('197', '421', 'Slovakia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('198', '386', 'Slovenia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('199', '44', 'Smaller Territories of the UK', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('2', '355', 'Albania', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('20', '375', 'Belarus', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('200', '677', 'Solomon Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('201', '252', 'Somalia', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('202', '27', 'South Africa', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('203', '0', 'South Georgia', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('204', '211', 'South Sudan', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('205', '34', 'Spain', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('206', '94', 'Sri Lanka', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('207', '249', 'Sudan', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('208', '597', 'Suriname', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('209', '47', 'Svalbard And Jan Mayen Islands', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('21', '32', 'Belgium', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('210', '268', 'Swaziland', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('211', '46', 'Sweden', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('212', '41', 'Switzerland', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('213', '963', 'Syria', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('214', '886', 'Taiwan', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('215', '992', 'Tajikistan', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('216', '255', 'Tanzania', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('217', '66', 'Thailand', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('218', '228', 'Togo', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('219', '690', 'Tokelau', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('22', '501', 'Belize', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('220', '676', 'Tonga', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('221', '1868', 'Trinidad And Tobago', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('222', '216', 'Tunisia', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('223', '90', 'Turkey', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('224', '7370', 'Turkmenistan', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('225', '1649', 'Turks And Caicos Islands', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('226', '688', 'Tuvalu', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('227', '256', 'Uganda', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('228', '380', 'Ukraine', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('229', '971', 'United Arab Emirates', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('23', '229', 'Benin', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('230', '44', 'United Kingdom', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('231', '1', 'United States', '7');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('232', '1', 'United States Minor Outlying Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('233', '598', 'Uruguay', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('234', '998', 'Uzbekistan', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('235', '678', 'Vanuatu', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('236', '39', 'Vatican City State (Holy See)', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('237', '58', 'Venezuela', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('238', '84', 'Vietnam', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('239', '1284', 'Virgin Islands (British)', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('24', '1441', 'Bermuda', '7');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('240', '1340', 'Virgin Islands (US)', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('241', '681', 'Wallis And Futuna Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('242', '212', 'Western Sahara', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('243', '967', 'Yemen', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('244', '38', 'Yugoslavia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('245', '260', 'Zambia', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('246', '263', 'Zimbabwe', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('25', '975', 'Bhutan', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('26', '591', 'Bolivia', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('27', '387', 'Bosnia and Herzegovina', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('28', '267', 'Botswana', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('29', '0', 'Bouvet Island', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('3', '213', 'Algeria', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('30', '55', 'Brazil', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('31', '246', 'British Indian Ocean Territory', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('32', '673', 'Brunei', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('33', '359', 'Bulgaria', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('34', '226', 'Burkina Faso', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('35', '257', 'Burundi', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('36', '855', 'Cambodia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('37', '237', 'Cameroon', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('38', '1', 'Canada', '7');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('39', '238', 'Cape Verde', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('4', '1684', 'American Samoa', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('40', '1345', 'Cayman Islands', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('41', '236', 'Central African Republic', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('42', '235', 'Chad', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('43', '56', 'Chile', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('44', '86', 'China', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('45', '61', 'Christmas Island', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('46', '672', 'Cocos (Keeling) Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('47', '57', 'Colombia', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('48', '269', 'Comoros', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('49', '242', 'Republic Of The Congo', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('5', '376', 'Andorra', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('50', '242', 'Democratic Republic Of The Congo', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('51', '682', 'Cook Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('52', '506', 'Costa Rica', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('53', '225', 'Cote DIvoire (Ivory Coast)', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('54', '385', 'Croatia (Hrvatska)', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('55', '53', 'Cuba', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('56', '357', 'Cyprus', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('57', '420', 'Czech Republic', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('58', '45', 'Denmark', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('59', '253', 'Djibouti', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('6', '244', 'Angola', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('60', '1767', 'Dominica', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('61', '1809', 'Dominican Republic', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('62', '670', 'East Timor', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('63', '593', 'Ecuador', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('64', '20', 'Egypt', '2');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('65', '503', 'El Salvador', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('66', '240', 'Equatorial Guinea', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('67', '291', 'Eritrea', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('68', '372', 'Estonia', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('69', '251', 'Ethiopia', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('7', '1264', 'Anguilla', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('70', '61', 'External Territories of Australia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('71', '500', 'Falkland Islands', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('72', '298', 'Faroe Islands', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('73', '679', 'Fiji Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('74', '358', 'Finland', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('75', '33', 'France', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('76', '594', 'French Guiana', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('77', '689', 'French Polynesia', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('78', '0', 'French Southern Territories', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('79', '241', 'Gabon', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('8', '0', 'Antarctica', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('80', '220', 'Gambia The', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('81', '995', 'Georgia', '5');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('82', '49', 'Germany', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('83', '233', 'Ghana', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('84', '350', 'Gibraltar', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('85', '30', 'Greece', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('86', '299', 'Greenland', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('87', '1473', 'Grenada', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('88', '590', 'Guadeloupe', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('89', '1671', 'Guam', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('9', '1268', 'Antigua And Barbuda', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('90', '502', 'Guatemala', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('91', '44', 'Guernsey and Alderney', '1');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('92', '224', 'Guinea', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('93', '245', 'Guinea-Bissau', '6');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('94', '592', 'Guyana', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('95', '509', 'Haiti', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('96', '0', 'Heard and McDonald Islands', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('97', '504', 'Honduras', '4');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('98', '852', 'Hong Kong S.A.R.', '3');
INSERT INTO `country` (`country_id`, `country_code`, `country_name`, `region_id`) VALUES ('99', '36', 'Hungary', '1');



INSERT INTO currency (country_name, currency) VALUES 
('Afghanistan', 'AFN'),
('Albania', 'ALL'),
('Algeria', 'DZD'),
('Andorra', 'EUR'),
('Angola', 'AOA'),
('Antigua and Barbuda', 'XCD'),
('Argentina', 'ARS'),
('Armenia', 'AMD'),
('Australia', 'AUD'),
('Austria', 'EUR'),
('Azerbaijan', 'AZN'),
('Bahamas', 'BSD'),
('Bahrain', 'BHD'),
('Bangladesh', 'BDT'),
('Barbados', 'BBD'),
('Belarus', 'BYN'),
('Belgium', 'EUR'),
('Belize', 'BZD'),
('Benin', 'XOF'),
('Bhutan', 'BTN'),
('Bolivia', 'BOB'),
('Bosnia and Herzegovina', 'BAM'),
('Botswana', 'BWP'),
('Brazil', 'BRL'),
('Brunei', 'BND'),
('Bulgaria', 'BGN'),
('Burkina Faso', 'XOF'),
('Burundi', 'BIF'),
('Cabo Verde', 'CVE'),
('Cambodia', 'KHR'),
('Cameroon', 'XAF'),
('Canada', 'CAD'),
('Central African Republic', 'XAF'),
('Chad', 'XAF'),
('Chile', 'CLP'),
('China', 'CNY'),
('Colombia', 'COP'),
('Comoros', 'KMF'),
('Congo', 'XAF'),
('Costa Rica', 'CRC'),
('Croatia', 'HRK'),
('Cuba', 'CUP'),
('Cyprus', 'EUR'),
('Czech Republic', 'CZK'),
('Democratic Republic of the Congo', 'CDF'),
('Denmark', 'DKK'),
('Djibouti', 'DJF'),
('Dominica', 'XCD'),
('Dominican Republic', 'DOP'),
('East Timor', 'USD'),
('Ecuador', 'USD'),
('Egypt', 'EGP'),
('El Salvador', 'USD'),
('Equatorial Guinea', 'XAF'),
('Eritrea', 'ERN'),
('Estonia', 'EUR'),
('Eswatini', 'SZL'),
('Ethiopia', 'ETB'),
('Fiji', 'FJD'),
('Finland', 'EUR'),
('France', 'EUR'),
('Gabon', 'XAF'),
('Gambia', 'GMD'),
('Georgia', 'GEL'),
('Germany', 'EUR'),
('Ghana', 'GHS'),
('Greece', 'EUR'),
('Grenada', 'XCD'),
('Guatemala', 'GTQ'),
('Guinea', 'GNF'),
('Guinea-Bissau', 'XOF'),
('Guyana', 'GYD'),
('Haiti', 'HTG'),
('Honduras', 'HNL'),
('Hungary', 'HUF'),
('Iceland', 'ISK'),
('India', 'INR'),
('Indonesia', 'IDR'),
('Iran', 'IRR'),
('Iraq', 'IQD'),
('Ireland', 'EUR'),
('Israel', 'ILS'),
('Italy', 'EUR'),
('Ivory Coast', 'XOF'),
('Jamaica', 'JMD'),
('Japan', 'JPY'),
('Jordan', 'JOD'),
('Kazakhstan', 'KZT'),
('Kenya', 'KES'),
('Kiribati', 'AUD'),
('Kosovo', 'EUR'),
('Kuwait', 'KWD'),
('Kyrgyzstan', 'KGS'),
('Laos', 'LAK'),
('Latvia', 'EUR'),
('Lebanon', 'LBP'),
('Lesotho', 'LSL'),
('Liberia', 'LRD'),
('Libya', 'LYD'),
('Liechtenstein', 'CHF'),
('Lithuania', 'EUR'),
('Luxembourg', 'EUR'),
('Madagascar', 'MGA'),
('Malawi', 'MWK'),
('Malaysia', 'MYR'),
('Maldives', 'MVR'),
('Mali', 'XOF'),
('Malta', 'EUR'),
('Marshall Islands', 'USD'),
('Mauritania', 'MRU'),
('Mauritius', 'MUR'),
('Mexico', 'MXN'),
('Micronesia', 'USD'),
('Moldova', 'MDL'),
('Monaco', 'EUR'),
('Mongolia', 'MNT'),
('Montenegro', 'EUR'),
('Morocco', 'MAD'),
('Mozambique', 'MZN'),
('Myanmar', 'MMK'),
('Namibia', 'NAD'),
('Nauru', 'AUD'),
('Nepal', 'NPR'),
('Netherlands', 'EUR'),
('New Zealand', 'NZD'),
('Nicaragua', 'NIO'),
('Niger', 'XOF'),
('Nigeria', 'NGN'),
('North Korea', 'KPW'),
('North Macedonia', 'MKD'),
('Norway', 'NOK'),
('Oman', 'OMR'),
('Pakistan', 'PKR'),
('Palau', 'USD'),
('Palestine', 'ILS'),
('Panama', 'PAB'),
('Papua New Guinea', 'PGK'),
('Paraguay', 'PYG'),
('Peru', 'PEN'),
('Philippines', 'PHP'),
('Poland', 'PLN'),
('Portugal', 'EUR'),
('Qatar', 'QAR'),
('Romania', 'RON'),
('Russia', 'RUB'),
('Rwanda', 'RWF'),
('Saint Kitts and Nevis', 'XCD'),
('Saint Lucia', 'XCD'),
('Saint Vincent and the Grenadines', 'XCD'),
('Samoa', 'WST'),
('San Marino', 'EUR'),
('Sao Tome and Principe', 'STN'),
('Saudi Arabia', 'SAR'),
('Senegal', 'XOF'),
('Serbia', 'RSD'),
('Seychelles', 'SCR'),
('Sierra Leone', 'SLL'),
('Singapore', 'SGD'),
('Slovakia', 'EUR'),
('Slovenia', 'EUR'),
('Solomon Islands', 'SBD'),
('Somalia', 'SOS'),
('South Africa', 'ZAR'),
('South Korea', 'KRW'),
('South Sudan', 'SSP'),
('Spain', 'EUR'),
('Sri Lanka', 'LKR'),
('Sudan', 'SDG'),
('Suriname', 'SRD'),
('Sweden', 'SEK'),
('Switzerland', 'CHF'),
('Syria', 'SYP'),
('Taiwan', 'TWD'),
('Tajikistan', 'TJS'),
('Tanzania', 'TZS'),
('Thailand', 'THB'),
('Togo', 'XOF'),
('Tonga', 'TOP'),
('Trinidad and Tobago', 'TTD'),
('Tunisia', 'TND');






INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('1', 'User', 'Edit User', 'User Setup', 'EDIT_USER');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('2', 'User', 'View User', 'User Setup', 'VIEW_USER');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('3', 'User', 'Create User', 'User Setup', 'CREATE_USER');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('4', 'Organisation', 'Create Organisation', 'Organisation', 'CREATE_ORGANISATION');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('5', 'Organisation', 'Delete Organisation', 'Organisation', 'DELETE_ORGANISATION');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('6', 'Organisation', 'Export Organisation', 'Organisation', 'EXPORT_ORGANISATION');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('7', 'Organisation', 'Update Organisation', 'Organisation', 'UPDATE_ORGANISATION');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('8', 'Organisation', 'View Organisation', 'Organisation', 'VIEW_ORGANISATION');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('9', 'Security Group', 'Admin Security Group', 'Security Group', 'SECURITY_GROUP_ADMIN');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('10', 'Security Group', 'View Security Group', 'Security Group', 'SECURITY_GROUP_READ');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('11', 'Security Group', 'Create Security Group', 'Security Group', 'SECURITY_GROUP_WRITE');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('12', 'User', 'Bulk Upload', 'User Setup', 'BULK_UPLOAD');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('13', 'User', 'Reset User Password', 'User Setup', 'RESET_USER_PASSWORD');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('14', 'User Login History', 'View Login History', 'User Login History', 'VIEW_LOGIN_HISTORY');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('15', 'Person', 'Create person', 'Person', 'CREATE_PERSON');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('16', 'Person', 'View Person', 'Person', 'VIEW_PERSON');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('17', 'Person', 'Edit Person', 'Person', 'EDIT_PERSON');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('18', 'Labour', 'Create Labour', 'Labour', 'CREATE_LABOUR');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('19', 'Labour', 'Edit Labour', 'Labour', 'EDIT_LABOUR');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('20', 'Labour', 'View Labour', 'Labour', 'VIEW_LABOUR');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('21', 'Craft', 'Create Craft', 'Craft', 'CREATE_CRAFT');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('22', 'Craft', 'Edit Craft', 'Craft', 'EDIT_CRAFT');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('23', 'Craft', 'View Craft', 'Craft', 'VIEW_CRAFT');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('24', 'Craft', 'Delete Craft', 'Craft', 'DELETE_CRAFT');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('25', 'Skills', 'Create Skills', 'Skills', 'CREATE_SKILL');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('26', 'Skills', 'Edit Skills', 'Skills', 'EDIT_SKILL');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('27', 'Skills', 'View Skills', 'Skills', 'VIEW_SKILL');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('28', 'Skills', 'Delete Skills', 'Skills', 'DELETE_SKILL');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('29', 'Site', 'Create Site', 'Site', 'CREATE_SITE');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('30', 'Site', 'Edit Site', 'Site', 'EDIT_SITE');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('31', 'Site', 'View Site', 'Site', 'VIEW_SITE');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('32', 'Site', 'Delete Site', 'Site', 'DELETE_SITE');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('33', 'Address', 'Create Address', 'Address', 'CREATE_ADDRESS');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('34', 'Address', 'Edit Address', 'Address', 'EDIT_ADDRESS');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('35', 'Address', 'View Address', 'Address', 'VIEW_ADDRESS');
INSERT INTO `permission` (`id`, `application`, `description`, `module_name`, `name`) VALUES ('36', 'Address', 'Delete Address', 'Address', 'DELETE_ADDRESS');




INSERT INTO `permission_group` (`id`, `default_group`, `description`, `is_in_use`, `name`, `organisation`) VALUES ('1',  1, 'Global Admin', 0, 'GLOBALADMIN', 1);
INSERT INTO `permission_group` (`id`,  `default_group`, `description`, `is_in_use`, `name`, `organisation`) VALUES ('2', 0, 'Group Admin', 0, 'GROUPADMIN', 1);


INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('1',  '1', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('2',  '2', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('3',  '3', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('4',  '4', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('5',  '5', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('6',  '6', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('7',  '7', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('8',  '8', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('9',  '9', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('10',  '10', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('11',  '11', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('12',  '12', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('13',  '13', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('14',  '14', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('15',  '15', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('16',  '16', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('17',  '17', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('18',  '18', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('19',  '19', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('20',  '20', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('21',  '21', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('22',  '22', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('23',  '23', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('24',  '24', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('25',  '25', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('26',  '26', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('27',  '27', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('28',  '28', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('29',  '29', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('30',  '30', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('31',  '31', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('32',  '32', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('33',  '33', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('34',  '34', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('35',  '35', '1');
INSERT INTO `permission_group_permissions` (`id`,`permission_id`, `permission_group_id`) VALUES ('36',  '36', '1');




INSERT INTO `organisation_master` (`id`, `description`,`status`, `organisation_id`, `organisation_name`) VALUES ('1', 'Global','ACTIVE', 'Global', 'Global');


INSERT INTO `user_details` (`id`, `address`,`user_keyword_id`, `contact_no`, `email_id` , `status`, `user_name`, `user_type`, `organisation_id`) VALUES ('1', '1969','admin', '1111111111' , 'testinfo@test.in', 'ACTIVE', 'admin', 'GLOBALADMIN', '1');


INSERT INTO `user_login` (`id`,  `deleted`, `is_enabled`, `is_not_first_time_logged_in`,`username`, `name`, `password`, `restricted`, `roles`, `user_id`) VALUES ('1',0, 0, 1,'admin', 'admin', '$2a$10$TghmxGCts1ruoej/L5VYDe7QaviVP1iAwOjhjKhwUpTTulZHOC2r2', 0, 'GLOBALADMIN', '1');

INSERT INTO `user_permission_group` (`id`, `organisation_id`, `permission_group_id`, `user_id`) VALUES ('1', '1', '1', '1');

