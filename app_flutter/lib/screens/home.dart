import 'package:app_flutter/model/user_model.dart';
import 'package:flutter/material.dart';

import '../widgets/list.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListUser(
      users: Repository.instance.userList,
    );
  }
}
