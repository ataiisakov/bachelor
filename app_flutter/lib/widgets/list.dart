import 'package:app_flutter/screens/detail.dart';
import 'package:flutter/material.dart';

import '../model/user_model.dart';

class ListUser extends StatefulWidget {
  const ListUser({super.key});

  @override
  State<StatefulWidget> createState() => _ListUser();
}

// List
class _ListUser extends State<ListUser> {
  late List<User> users;

  @override
  void initState() {
    users = Repository.instance.userList;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: users.length, itemBuilder: _buildListTile);
  }

  Widget _buildListTile(BuildContext context, int index) {
    return UserTile(user: users.elementAt(index));
  }
}

// List Item
class UserTile extends StatelessWidget {
  UserTile({required this.user}) : super(key: ObjectKey(user));

  final User user;

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(user.name),
      onTap: () {
        Navigator.push(context,
            MaterialPageRoute(builder: (context) => DetailScreen(user: user)));
      },
      leading: CircleAvatar(
        backgroundImage: NetworkImage(user.photoUrl),
      ),
    );
  }
}
