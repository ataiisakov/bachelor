
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
    return _buildList();
  }
  Widget _buildList(){
    return ListView.builder(
        itemCount: users.length,
        itemBuilder: (BuildContext context, int index) {
          if (index == 0) {
            return Column(
              children: [
                _buildHeaderFooter("Header"),
                _buildListTile(context, index)
              ],
            );
          }
          if (index == users.length - 1) {
            return Column(
              children: [
                _buildListTile(context, index),
                _buildHeaderFooter("Footer")
              ],
            );
          }
          return _buildListTile(context, index);
        });
  }

  Widget _buildListTile(BuildContext context, int index) {
    return UserTile(user: users.elementAt(index));
  }

  Widget _buildHeaderFooter(String text) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 6),
      child: Card(
        child: ListTile(
          contentPadding:
              const EdgeInsets.symmetric(horizontal: 20, vertical: 15),
          title: Text(text),
        ),
      ),
    );
  }
}

// List Item
class UserTile extends StatelessWidget {
  UserTile({required this.user}) : super(key: ObjectKey(user));

  final User user;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 6),
      child: Card(
          child: ListTile(
            contentPadding: const EdgeInsets.symmetric(horizontal: 20,vertical: 15),
            title: Text(user.name),
            onTap: () {
              Navigator.of(context)
                  .push(_createRoute(DetailScreen(user: user)));
            },
            leading: CircleAvatar(
              backgroundImage: NetworkImage(user.photoUrl),
            ),
          ),
        ));
  }

  Route _createRoute(Widget param) {
    return PageRouteBuilder(
        pageBuilder: (context, animation, secondaryAnimation) => param,
        transitionsBuilder: (context, animation, secondaryAnimation, child) {
          const begin = Offset(1.0, 0.0);
          const end = Offset.zero;
          const curve = Curves.ease;
          var tweet =
              Tween(begin: begin, end: end).chain(CurveTween(curve: curve));
          return SlideTransition(
            position: animation.drive(tweet),
            child: child,
          );
        });
  }
}
